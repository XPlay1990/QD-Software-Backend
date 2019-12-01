package com.nicando.ediportal.database.repositories.ediConnection.question

import com.nicando.ediportal.database.model.edi.questions.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 01.12.2019, So.
 **/
@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
}