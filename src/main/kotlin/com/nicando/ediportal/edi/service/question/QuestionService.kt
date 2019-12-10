package com.nicando.ediportal.edi.service.question

import com.nicando.ediportal.user.service.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.ediConnection.QuestionResponse
import com.nicando.ediportal.edi.database.repository.question.QuestionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class QuestionService(private val questionRepository: QuestionRepository,
                      private val authenticationInfoService: AuthenticationInfoService) {


    fun getAllQuestions(language: String): MutableList<QuestionResponse> {
        val questions = questionRepository.findAll()
        val questionsLocalized = mutableListOf<QuestionResponse>()
        for (question in questions) {
            if (language == "de") {
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