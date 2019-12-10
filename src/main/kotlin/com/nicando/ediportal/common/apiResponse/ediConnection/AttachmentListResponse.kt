package com.nicando.ediportal.common.apiResponse.ediConnection

import com.nicando.ediportal.edi.database.model.Attachment


/**
 * @author : j_ada
 * @since : 12.09.2019, Do.
 **/

data class AttachmentListResponse(
        val content: MutableSet<Attachment>
)