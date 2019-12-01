package com.nicando.ediportal.rest.edi.questions

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.ediConnection.EdiConnectionAccessService
import com.nicando.ediportal.common.ediConnection.EdiConnectionService
import com.nicando.ediportal.common.exceptions.rest.ForbiddenException
import com.nicando.ediportal.database.model.edi.questions.Answer
import com.nicando.ediportal.database.model.edi.questions.TransferStandards
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
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
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGISTERED_USER')")
@RestController
@RequestMapping("/edi_connection/question/answer")
class AnswerController(private val ediConnectionService: EdiConnectionService,
                       private val ediConnectionAccessService: EdiConnectionAccessService,
                       private val authenticationInfoService: AuthenticationInfoService) {
    @GetMapping("/{id}")
    fun getAnswers(@PathVariable("id") ediConnectionId: Long, request: HttpServletRequest): MutableSet<Answer> {
        val ediConnection = ediConnectionService.findEdiConnection(ediConnectionId)
        ediConnectionAccessService.hasUserAccessToEdiConnection(request, ediConnection,
                "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                        "tried to get Answers of Edi-Connection with id: $ediConnectionId which he is not allowed to!")

        logger.info("Getting Edi Connection answers for User ${authenticationInfoService.getUsernameFromAuthentication()}")
        return ediConnection.questionCatalog.answers
    }

    @PostMapping("/{id}")
    fun saveAnswers(@PathVariable("id") ediConnectionId: Long, @RequestBody answerList: MutableSet<Answer>, request: HttpServletRequest): ResponseMessage {
        val ediConnection = ediConnectionService.findEdiConnection(ediConnectionId)
        ediConnectionAccessService.hasUserAccessToEdiConnection(request, ediConnection,
                "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                        "tried to save Answers to Edi-Connection with id: $ediConnectionId which he is not allowed to!")

        logger.info("Saving Answers for Ediconnection with id ${ediConnection.id} done by User ${authenticationInfoService.getUsernameFromAuthentication()}")
        ediConnectionService.saveAnswers(ediConnection, answerList)
        return ResponseMessage(true, "Successfully saved Answers.")
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}