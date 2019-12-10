package com.nicando.ediportal.common.apiResponse.ediConnection.message

import com.nicando.ediportal.edi.database.model.message.Message

/**
 * @author : j_ada
 * @since : 18.07.2019, Do
 **/
data class EdiMessageResponse(
        val message: Message,
        val type: String
)