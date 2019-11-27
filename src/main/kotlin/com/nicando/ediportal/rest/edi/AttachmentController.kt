package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.common.apiResponse.AttachmentListResponse
import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.apiResponse.ediConnection.UploadFileResponse
import com.nicando.ediportal.common.ediConnection.AttachmentService
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
import javax.servlet.http.HttpServletRequest


/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/

@RestController
@RequestMapping("/edi_connection/{ediConnectionId}/attachment")
class AttachmentController(private val attachmentService: AttachmentService) {

    @GetMapping
    fun getFileList(@PathVariable ediConnectionId: Long): AttachmentListResponse {
        return attachmentService.getFileList(ediConnectionId)
    }

    @PostMapping("/upload")
    fun uploadFile(@PathVariable ediConnectionId: Long, @RequestParam("file") file: MultipartFile): ResponseMessage {
        return try {
            val fileName = attachmentService.storeFile("$EDICONNECTIONFOLDER/$ediConnectionId", file, ediConnectionId)

            val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/$ediConnectionId/")
                    .path(fileName)
                    .toUriString()

            ResponseMessage(true, UploadFileResponse(fileName, fileDownloadUri,
                    file.contentType, file.size))
        } catch (ex: Exception) {
            ResponseMessage(false, ex.message.toString())
        }
    }

//    @PostMapping("/edi_connection/uploadMultipleFiles/{ediConnectionId}")
//    fun uploadMultipleFiles(@PathVariable ediConnectionId: Long, @RequestParam("files") files: Array<MultipartFile>): MutableList<UploadFileResponse> {
//        return files.toList()
//                .stream()
//                .map { file -> uploadFile(ediConnectionId, file) }
//                .collect(Collectors.toList())
//    }

    @GetMapping("/download/{fileName:.+}")
    fun downloadFile(@PathVariable ediConnectionId: Long, @PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource = attachmentService.loadFileAsResource("$EDICONNECTIONFOLDER/$ediConnectionId", fileName)

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
        private const val EDICONNECTIONFOLDER = "ediconnection"
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}