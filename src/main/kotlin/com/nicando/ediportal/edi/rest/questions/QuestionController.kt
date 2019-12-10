package com.nicando.ediportal.edi.rest.questions

import com.nicando.ediportal.user.service.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.ediConnection.QuestionResponse
import com.nicando.ediportal.edi.service.question.QuestionService
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


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
@RequestMapping("/edi_connection/question/list")
class QuestionController(private val authenticationInfoService: AuthenticationInfoService,
                         private val questionService: QuestionService) {
    @GetMapping()
    fun getQuestions(@RequestParam language: String): MutableList<QuestionResponse> {
        logger.info("Getting all Edi-Questions for User ${authenticationInfoService.getUsernameFromAuthentication()}, Language: $language")
        return questionService.getAllQuestions(language)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}