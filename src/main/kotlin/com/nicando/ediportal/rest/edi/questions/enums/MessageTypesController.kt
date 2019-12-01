package com.nicando.ediportal.rest.edi.questions.enums

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.edi.questions.enums.ConnectionTypes
import com.nicando.ediportal.database.model.edi.questions.enums.MessageTypes
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


/**
 * Copyright (C) 2019-2019 Jan Adamczyk <j_adamczyk@hotmail.com>
 *
 * Created: 22.06.19
 * This file is part of ediportal
 *
 * This Code can not be copied and/or distributed without the express
 * permission of Jan Adamczyk
 */
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGISTERED_USER')")
@RestController
@RequestMapping("/edi_connection/question/messageTypes")
class MessageTypesController(private val authenticationInfoService: AuthenticationInfoService) {
    @GetMapping
    fun getMessageTypes(): Array<MessageTypes> {
        logger.info("Getting message types for User ${authenticationInfoService.getUsernameFromAuthentication()}")
        return MessageTypes.values()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}