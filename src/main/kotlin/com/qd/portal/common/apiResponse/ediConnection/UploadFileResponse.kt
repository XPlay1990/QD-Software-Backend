package com.qd.portal.common.apiResponse.ediConnection

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