package com.nicando.ediportal.common.ediConnection.message

import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageResponse
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Jan Adamczyk on 19.08.2019.
 */
@Service
class EdiConnectionMessageService(private val ediConnectionRepository: EdiConnectionRepository, private val userRepository: UserRepository) {
    @Transactional
    fun saveMessage(ediConnection: EdiConnection, message: String, currentUser: UserPrincipal) {
        val user = userRepository.findByUsername(currentUser.username)
        if (user == null) {
            logger.error("Tried to save Message as non-existent user!")
            throw IllegalStateException("Tried to save Message as non-existent user!")
        }
        ediConnection.messages.add(Message(sender = user, text = message))

        ediConnectionRepository.save(ediConnection)
    }

    fun findEdiMessages(ediConnection: EdiConnection): EdiMessageListResponse {
        //TODO: Find better way to get Messages instead of pulling whole connection.
        //          BIDIRECTIONAL IS NOT!!! A BETTER SOLUTION! :>
        val messages = ediConnection.messages
        val ediMessageResponses = mutableListOf<EdiMessageResponse>()
        messages.forEach { message ->
            ediMessageResponses.add(EdiMessageResponse(message, message.javaClass.toString()))
        }
        return EdiMessageListResponse(ediMessageResponses)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}