package com.nicando.ediportal.common.apiResponse

import com.nicando.ediportal.database.model.edi.message.Attachment


/**
 * @author : j_ada
 * @since : 12.09.2019, Do.
 **/

data class AttachmentListResponse(
        val content: MutableSet<Attachment>
)