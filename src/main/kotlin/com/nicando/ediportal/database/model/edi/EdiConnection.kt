package com.nicando.ediportal.database.model.edi

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.nicando.ediportal.database.model.edi.message.Attachment
import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.edi.questions.QuestionCatalog
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.model.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class EdiConnection(

        @ManyToOne
        var customer: Organization,

        @ManyToOne
        var supplier: Organization
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Enumerated(EnumType.STRING)
    var status: EdiStatus = EdiStatus.NEW

    @JsonFormat(pattern="dd-MM-yyyy (HH:mm)")
    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @JsonFormat(pattern="dd-MM-yyyy (HH:mm)")
    @UpdateTimestamp
    var updateTime: LocalDateTime? = null

    @ManyToOne
    var assignedDeveloper: User? = null

    @ManyToMany
    var customerContacts: MutableList<User> = mutableListOf()

    @ManyToMany
    var supplierContacts: MutableList<User> = mutableListOf()

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var attachments: MutableSet<Attachment>? = null

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var messages: MutableSet<Message>? = null

    @JsonIgnore
    @OneToOne(cascade = [CascadeType.ALL])
    var questionCatalog: QuestionCatalog? = null

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var devComments: MutableSet<Message>? = null

    var readByCustomer: Boolean = false
    var readBySupplier: Boolean = false
    var readByNicando: Boolean = false
}

enum class EdiStatus{
    NEW,
    DEVELOPMENT,
    WAITING_FOR_CUSTOMER,
    WAITING_FOR_SUPPLIER,
    DONE
}