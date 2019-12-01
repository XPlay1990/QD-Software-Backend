package com.nicando.ediportal.common.ediConnection.question

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.questions.Question
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.ediConnection.question.QuestionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class QuestionService(private val questionRepository: QuestionRepository,
                      private val authenticationInfoService: AuthenticationInfoService) {


    fun getAllQuestions(): MutableList<Question> {
        return questionRepository.findAll()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}