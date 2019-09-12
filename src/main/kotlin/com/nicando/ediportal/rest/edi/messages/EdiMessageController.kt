package com.nicando.ediportal.rest.edi.messages

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.EdiConnectionAccessService
import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.nicando.ediportal.common.ediConnection.EdiConnectionListService
import com.nicando.ediportal.common.ediConnection.message.EdiConnectionMessageService
import com.nicando.ediportal.common.exceptions.rest.ForbiddenException
import com.nicando.ediportal.security.CurrentUser
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
@RestController
@RequestMapping("/edi_connection/{ediConnectionId}/messages")
class EdiMessageController(private val ediConnectionAccessService: EdiConnectionAccessService,
                           private val ediConnectionListService: EdiConnectionListService,
                           private val authenticationInfoService: AuthenticationInfoService,
                           private val ediConnectionMessageService: EdiConnectionMessageService) {

    @PostMapping
    fun addMessage(request: HttpServletRequest, @CurrentUser currentUser: UserPrincipal, @PathVariable ediConnectionId: Long, @RequestBody message: String): ResponseEntity<ResponseMessage> {
        val foundEdiConnection = ediConnectionListService.findEdiConnection(ediConnectionId)

        if (!ediConnectionAccessService.hasUserAccessToEdiConnection(request, foundEdiConnection.content)) {
            logger.warn("User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                    "tried to add Message to Edi-Connection with id: $ediConnectionId which he is not allowed to!")
            throw ForbiddenException("You are not allowed to access this Edi-Connection!")
        }
        ediConnectionMessageService.saveMessage(foundEdiConnection.content, message, currentUser)
        return ResponseEntity.ok(ResponseMessage(true, "Successfully saved your Message!"))
    }

    @GetMapping
    fun getMessages(request: HttpServletRequest, @PathVariable ediConnectionId: Long): EdiMessageListResponse {
        val foundEdiConnection = ediConnectionListService.findEdiConnection(ediConnectionId)

        if (!ediConnectionAccessService.hasUserAccessToEdiConnection(request, foundEdiConnection.content)) {
            logger.warn("User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                    "tried to get Message to Edi-Connection with id: $ediConnectionId which he is not allowed to!")
            throw ForbiddenException("You are not allowed to access this Edi-Connection!")
        }
        return ediConnectionMessageService.findEdiMessages(foundEdiConnection.content)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}