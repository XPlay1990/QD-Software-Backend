package com.nicando.ediportal.database.model.edi

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class QuestionCatalog (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long
)