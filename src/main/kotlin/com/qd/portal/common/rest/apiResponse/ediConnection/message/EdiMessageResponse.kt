package com.qd.portal.common.rest.apiResponse.ediConnection.message

import com.qd.portal.edi.database.model.message.Message

/**
 * @author : j_ada
 * @since : 18.07.2019, Do
 **/
data class EdiMessageResponse(
        val message: Message,
        val type: String
)