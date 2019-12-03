package com.nicando.ediportal.common.ediConnection.question

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.apiResponse.AnswerResponse
import com.nicando.ediportal.common.ediConnection.EdiConnectionAccessService
import com.nicando.ediportal.common.exceptions.rest.ForbiddenException
import com.nicando.ediportal.database.model.edi.questions.Answer
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import com.nicando.ediportal.database.repositories.ediConnection.question.QuestionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

/**
 * @author : j_ada
 * @since : 03.12.2019, Di.
 **/

@Service
class AnswerService(private val ediConnectionRepository: EdiConnectionRepository,
                    private val questionRepository: QuestionRepository,
                    private val ediConnectionAccessService: EdiConnectionAccessService,
                    private val authenticationInfoService: AuthenticationInfoService) {


    @Transactional
    fun getAnswers(ediConnectionId: Long, request: HttpServletRequest): MutableSet<AnswerResponse> {
        val ediConnection = ediConnectionRepository.findById(ediConnectionId).get()
        ediConnectionAccessService.hasUserAccessToEdiConnection(request, ediConnection,
                "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                        "tried to get Answers to Edi-Connection with id: $ediConnectionId which he is not allowed to!")

        logger.info("Getting Answers for Ediconnection with id ${ediConnection.id} for User ${authenticationInfoService.getUsernameFromAuthentication()}")
        val answerResponseList = mutableSetOf<AnswerResponse>()
        ediConnection.questionCatalog.answers.forEach { answer ->
            answerResponseList.add(AnswerResponse(answer.question.id, answer.answer))
        }
        return answerResponseList
    }

    @Transactional
    fun saveAnswers(ediConnectionId: Long, answerResponse: MutableSet<AnswerResponse>, request: HttpServletRequest) {
        val ediConnection = ediConnectionRepository.findById(ediConnectionId).get()
        try {
            ediConnectionAccessService.isUserSupplierOfEdiConnection(request, ediConnection,
                    "User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                            "tried to save Answers to Edi-Connection with id: $ediConnectionId which he is not allowed to!")
        } catch (e: ForbiddenException) {
            throw ForbiddenException("You are not allowed to save Answers for this Edi-Connection!")
        }

        logger.info("Saving Answers for Ediconnection with id ${ediConnection.id} done by User ${authenticationInfoService.getUsernameFromAuthentication()}")
        val answerList = mutableSetOf<Answer>()
        answerResponse.forEach { element ->
            answerList.add(Answer(questionRepository.findById(element.questionId).get(), element.answer))
        }
        ediConnection.questionCatalog.answers.clear()
        ediConnection.questionCatalog.answers.addAll(answerList)
        ediConnectionRepository.save(ediConnection)
    }


    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}