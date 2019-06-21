package com.nicando.ediportal.database.model.edi

import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.organization.Organization
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class EdiConnection(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        var status: String,

        @ManyToOne
        var customer: Organization,

        @ManyToOne
        var supplier: Organization,

        @OneToOne
        var questionCatalog: QuestionCatalog?,

        @OneToMany(cascade = [CascadeType.ALL])
        var messages: MutableSet<Message>
)