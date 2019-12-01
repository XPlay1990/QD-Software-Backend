package com.nicando.ediportal.rest.edi.questions

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.edi.questions.TransferStandards
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


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
@RequestMapping("/edi_connection/question/transferStandards")
class TransferStandardsController(private val authenticationInfoService: AuthenticationInfoService) {

    @GetMapping()
    fun getTransferStandards(): Array<TransferStandards> {
        logger.info("Getting Transfer Standards for User ${authenticationInfoService.getUsernameFromAuthentication()}")
        return TransferStandards.values()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}