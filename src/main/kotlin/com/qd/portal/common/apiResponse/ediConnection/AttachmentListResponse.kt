package com.qd.portal.common.apiResponse.ediConnection

import com.qd.portal.edi.database.model.Attachment


/**
 * @author : j_ada
 * @since : 12.09.2019, Do.
 **/

data class AttachmentListResponse(
        val content: MutableSet<Attachment>
)