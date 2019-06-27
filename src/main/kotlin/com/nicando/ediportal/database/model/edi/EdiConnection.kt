package com.nicando.ediportal.database.model.edi

import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.edi.questions.QuestionCatalog
import com.nicando.ediportal.database.model.organization.Organization
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class EdiConnection(

        var status: String,

        @ManyToOne
        var customer: Organization,

        @ManyToOne
        var supplier: Organization,

        @OneToOne(cascade = [CascadeType.ALL])
        var questionCatalog: QuestionCatalog?,

        @OneToMany(cascade = [CascadeType.ALL])
        var messages: MutableSet<Message>
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}