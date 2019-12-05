package com.nicando.ediportal.common.apiResponse.ediConnection

import com.nicando.ediportal.database.model.edi.Attachment


/**
 * @author : j_ada
 * @since : 12.09.2019, Do.
 **/

data class AttachmentListResponse(
        val content: MutableSet<Attachment>
)