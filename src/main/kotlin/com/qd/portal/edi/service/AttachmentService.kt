package com.qd.portal.edi.service

import com.qd.portal.common.rest.apiResponse.ediConnection.AttachmentListResponse
import com.qd.portal.common.properties.AppProperties
import com.qd.portal.edi.database.model.Attachment
import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.repository.EdiConnectionRepository
import com.qd.portal.common.exceptions.attachments.FileStorageException
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/

@Service
class AttachmentService(private val ediConnectionRepository: EdiConnectionRepository,
                        appProperties: AppProperties) {

    private val UPLOAD_DIR: String = appProperties.constants.uploadDirectory

    fun getFileList(ediConnectionId: Long): AttachmentListResponse {
        return AttachmentListResponse(ediConnectionRepository.findById(ediConnectionId).get().attachments)
    }

    fun storeFile(directoryName: String, file: MultipartFile, ediconnectionId: Long): String {
        // Normalize file name
        val fileName = if (file.originalFilename.isNullOrBlank()) {
            "unnamed"
        } else {
            StringUtils.cleanPath(file.originalFilename!!)
        }

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Filename $fileName contains invalid Characters!")
            }

            // Copy file to the target location (Replacing existing file with the same name)
            val filePath = createAndGetFilePath(directoryName, fileName)
            Files.copy(file.inputStream, filePath)

            val ediConnection: EdiConnection = ediConnectionRepository.findById(ediconnectionId).get()
            var contentType = file.contentType
            if (contentType == null) {
                contentType = "unknown"
            }
            ediConnection.attachments.add(Attachment(fileName, contentType, file.size))
            ediConnectionRepository.save(ediConnection)

            return fileName
        } catch (ex: FileAlreadyExistsException) {
            logger.error("Tried to upload a file that already existed")
            throw FileStorageException("A file with that filename already exists!")
        }
    }

    fun loadFileAsResource(directoryName: String, fileName: String): Resource {
        val filePath = getFilePath(directoryName, fileName)
        val resource = UrlResource(filePath.toUri())
        return if (resource.exists()) {
            resource
        } else {
            throw FileNotFoundException("File not found $fileName")
        }
    }

    private fun getFilePath(directoryName: String, fileName: String): Path {
        val storePath = Paths.get("$UPLOAD_DIR/$directoryName").toAbsolutePath().normalize()
        return storePath.resolve(fileName).normalize()
    }

    private fun createAndGetFilePath(directoryName: String, fileName: String): Path {
        val storePath = Paths.get("$UPLOAD_DIR/$directoryName").toAbsolutePath().normalize()
        Files.createDirectories(storePath)
        return storePath.resolve(fileName).normalize()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}