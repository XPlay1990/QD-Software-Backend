package com.nicando.ediportal.rest.edi.messages

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/

@RestController
class FileController(private val fileStorageService: FileStorageService) {

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponse {
        val fileName = fileStorageService.storeFile(file)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString()

        logger.warn(UploadFileResponse(fileName, fileDownloadUri,
                file.contentType, file.size).toString())

        return UploadFileResponse(fileName, fileDownloadUri,
                file.contentType, file.size)
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>): MutableList<UploadFileResponse> {
        return files.toList()
                .stream()
                .map { file -> uploadFile(file) }
                .collect(Collectors.toList())
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource = fileStorageService.loadFileAsResource(fileName)

        // Try to determine file's content type
        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: IOException) {
            logger.info("Could not determine file type.")
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
                .body<Resource>(resource)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}