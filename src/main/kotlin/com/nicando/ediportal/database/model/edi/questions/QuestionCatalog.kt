package com.nicando.ediportal.database.model.edi.questions

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class QuestionCatalog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToMany(cascade = [CascadeType.ALL])
        var answers: MutableSet<Answer>
)