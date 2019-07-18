package com.nicando.ediportal.common.ediConnection

import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageResponse
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EdiConnectionService(private val ediConnectionRepository: EdiConnectionRepository) {

    fun findEdiMessages(ediConnectionId: Long): EdiMessageListResponse {
        //TODO: Find better way to get Messages instead of pulling whole connection.
        //          BIDIRECTIONAL IS NOT!!! A BETTER SOLUTION! :>
        val messages = ediConnectionRepository.findById(ediConnectionId).get().messages
        val ediMessageResponses = mutableListOf<EdiMessageResponse>()
        messages?.forEach { message ->
            ediMessageResponses.add(EdiMessageResponse(message, message.javaClass.toString()))
        }
        return EdiMessageListResponse(ediMessageResponses)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}