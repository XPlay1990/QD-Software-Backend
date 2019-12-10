package com.nicando.ediportal.edi.database.model.questions

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
class QuestionCatalog {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        var answers: MutableSet<Answer> = mutableSetOf()
}