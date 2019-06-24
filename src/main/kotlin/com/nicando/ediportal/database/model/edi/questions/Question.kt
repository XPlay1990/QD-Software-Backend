package com.nicando.ediportal.database.model.edi.questions

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class Question(
        val question_de: String,
        val question_en: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}