package com.qd.portal.edi.database.repository.question

import com.qd.portal.edi.database.model.questions.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 01.12.2019, So.
 **/
@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
}