package com.nicando.ediportal.common.ediConnection.question

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.ediConnection.QuestionResponse
import com.nicando.ediportal.database.repositories.ediConnection.question.QuestionRepository
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