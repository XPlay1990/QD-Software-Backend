package com.qd.portal.edi.rest.messages

import com.qd.portal.user.service.AuthenticationInfoService
import com.qd.portal.common.apiResponse.ResponseMessage
import com.qd.portal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.qd.portal.edi.service.EdiConnectionAccessService
import com.qd.portal.edi.service.EdiConnectionService
import com.qd.portal.edi.service.message.EdiConnectionMessageService
import com.qd.portal.security.CurrentUser
import com.qd.portal.security.UserPrincipal
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
                           private val ediConnectionService: EdiConnectionService,
                           private val authenticationInfoService: AuthenticationInfoService,
                           private val ediConnectionMessageService: EdiConnectionMessageService) {

    @PostMapping
    fun addMessage(request: HttpServletRequest, @CurrentUser currentUser: UserPrincipal, @PathVariable ediConnectionId: Long, @RequestBody message: String): ResponseEntity<ResponseMessage> {
        val foundEdiConnection = ediConnectionService.findEdiConnection(ediConnectionId)

        ediConnectionAccessService.hasUserAccessToEdiConnection(request, foundEdiConnection,
                "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                        "tried to add Message to Edi-Connection with id: $ediConnectionId which he is not allowed to!")

        ediConnectionMessageService.saveMessage(foundEdiConnection, message, currentUser)
        return ResponseEntity.ok(ResponseMessage(true, "Successfully saved your Message!"))
    }

    @GetMapping
    fun getMessages(request: HttpServletRequest, @PathVariable ediConnectionId: Long): EdiMessageListResponse {
        val foundEdiConnection = ediConnectionService.findEdiConnection(ediConnectionId)

        ediConnectionAccessService.hasUserAccessToEdiConnection(request, foundEdiConnection,
                "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                        "tried to get Message to Edi-Connection with id: $ediConnectionId which he is not allowed to!")

        return ediConnectionMessageService.findEdiMessages(foundEdiConnection)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}