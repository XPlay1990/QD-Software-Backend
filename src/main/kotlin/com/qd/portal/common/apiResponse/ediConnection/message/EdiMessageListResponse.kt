package com.qd.portal.common.apiResponse.ediConnection.message

/**
 * @author : j_ada
 * @since : 18.07.2019, Do
 **/
data class EdiMessageListResponse(
        val content: MutableList<EdiMessageResponse>?
)