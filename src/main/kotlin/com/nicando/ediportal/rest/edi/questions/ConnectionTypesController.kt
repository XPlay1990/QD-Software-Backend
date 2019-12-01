package com.nicando.ediportal.rest.edi.questions

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.edi.questions.ConnectionTypes
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
@RequestMapping("/edi_connection/question/connectionTypes")
class ConnectionTypesController(private val authenticationInfoService: AuthenticationInfoService) {
    @GetMapping()
    fun getConnectionTypes(): Array<ConnectionTypes> {
        logger.info("Getting Connection Types for User ${authenticationInfoService.getUsernameFromAuthentication()}")
        return ConnectionTypes.values()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}