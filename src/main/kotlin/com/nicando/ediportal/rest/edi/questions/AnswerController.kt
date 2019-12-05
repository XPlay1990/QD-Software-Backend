package com.nicando.ediportal.rest.edi.questions

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.ediConnection.AnswerResponse
import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.ediConnection.question.AnswerService
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
@RequestMapping("/edi_connection/{id}/question/answer")
class AnswerController(private val answerService:AnswerService,
                       private val authenticationInfoService: AuthenticationInfoService) {
    @GetMapping
    fun getAnswers(@PathVariable("id") ediConnectionId: Long, request: HttpServletRequest): MutableSet<AnswerResponse> {
        return answerService.getAnswers(ediConnectionId, request)
    }

    @PutMapping
    fun saveAnswers(@PathVariable("id") ediConnectionId: Long, @RequestBody answerList: MutableSet<AnswerResponse>, request: HttpServletRequest): ResponseMessage {
        answerService.saveAnswers(ediConnectionId, answerList, request)
        return ResponseMessage(true, "Successfully saved Answers.")
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}