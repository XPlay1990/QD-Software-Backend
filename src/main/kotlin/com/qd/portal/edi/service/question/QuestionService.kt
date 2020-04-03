package com.qd.portal.edi.service.question

import com.qd.portal.user.service.AuthenticationInfoService
import com.qd.portal.common.rest.apiResponse.ediConnection.QuestionResponse
import com.qd.portal.edi.database.repository.question.QuestionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class QuestionService(private val questionRepository: QuestionRepository,
                      private val authenticationInfoService: AuthenticationInfoService) {


    fun getAllQuestions(language: String): MutableList<QuestionResponse> {
        val questions = questionRepository.findAll()
        val questionsLocalized = mutableListOf<QuestionResponse>()
        for (question in questions) {
            if (language == "de" || language == "de-DE") {
                questionsLocalized.add(QuestionResponse(question.id, question.question_de))
            } else {
                questionsLocalized.add(QuestionResponse(question.id, question.question_en))
            }
        }
        return questionsLocalized
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}