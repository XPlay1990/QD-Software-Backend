package com.nicando.ediportal.rest.edi.messages

/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/
data class UploadFileResponse(
        val fileName: String,
        val fileDownloadUri: String,
        val fileType: String?,
        val size: Long
)