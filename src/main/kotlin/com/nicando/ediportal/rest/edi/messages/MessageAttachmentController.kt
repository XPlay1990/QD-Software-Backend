package com.nicando.ediportal.rest.edi.messages

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.ContentDisposition
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
class MessageAttachmentController(private val fileStorageService: FileStorageService) {

    @PostMapping("/upload/{messageId}")
    fun uploadFile(@PathVariable messageId: Long, @RequestParam("file") file: MultipartFile): UploadFileResponse {
        val fileName = fileStorageService.storeFile("messages/$messageId", file)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/$messageId/")
                .path(fileName)
                .toUriString()

        return UploadFileResponse(fileName, fileDownloadUri,
                file.contentType, file.size)
    }

    @PostMapping("/uploadMultipleFiles/{messageId}")
    fun uploadMultipleFiles(@PathVariable messageId: Long, @RequestParam("files") files: Array<MultipartFile>): MutableList<UploadFileResponse> {
        return files.toList()
                .stream()
                .map { file -> uploadFile(messageId, file) }
                .collect(Collectors.toList())
    }

    @GetMapping("/downloadFile/{messageId}/{fileName:.+}")
    fun downloadFile(@PathVariable messageId: Long, @PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource = fileStorageService.loadFileAsResource("messages/$messageId", fileName)

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
        val contentDisposition = ContentDisposition.builder("inline")
                .filename(resource.filename!!)
                .build()

        val headers = HttpHeaders()
        headers.contentDisposition = contentDisposition
//        headers.accessControlExposeHeaders = mutableListOf(HttpHeaders.CONTENT_DISPOSITION)

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(resource.contentLength())
                .body(resource)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}