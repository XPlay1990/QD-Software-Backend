package com.nicando.ediportal.rest.edi.messages

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/

@Service
//class FileStorageService (private val fileStorageProperties: FileStorageProperties) {
class FileStorageService() {

    private val fileStorageLocation: Path = Paths.get("./upload/")
            .toAbsolutePath().normalize()

    init {

        Files.createDirectories(this.fileStorageLocation)
    }

    fun storeFile(file: MultipartFile): String {
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
        val targetLocation = this.fileStorageLocation.resolve(fileName)
        Files.copy(file.inputStream, targetLocation)

        return fileName
        } catch (ex: FileAlreadyExistsException) {
            throw FileStorageException(ex.toString(), ex)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        val filePath = this.fileStorageLocation.resolve(fileName).normalize()
        val resource = UrlResource(filePath.toUri())
        return if (resource.exists()) {
            resource
        } else {
            throw FileNotFoundException("File not found $fileName")
        }
    }
}