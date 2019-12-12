package com.qd.portal.edi.database.model.questions

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class Question(
        @Column(length = 1000)
        val question_de: String,

        @Column(length = 1000)
        val question_en: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}