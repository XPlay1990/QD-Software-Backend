package com.nicando.ediportal.edi.database.model.questions

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class Answer(
        @ManyToOne
        val question: Question,

        var answer: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}