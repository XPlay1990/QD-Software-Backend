package com.qd.portal.edi.database.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.qd.portal.edi.database.model.message.Message
import com.qd.portal.edi.database.model.questions.QuestionCatalog
import com.qd.portal.organization.database.model.Organization
import com.qd.portal.user.database.model.User
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

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
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
    var attachments: MutableSet<Attachment> = mutableSetOf()

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var messages: MutableList<Message> = mutableListOf()

    @JsonIgnore
    @OneToOne(cascade = [CascadeType.ALL])
    var questionCatalog: QuestionCatalog = QuestionCatalog()

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var devComments: MutableList<Message> = mutableListOf()

    @JsonIgnore
    var readByCustomer: Boolean = false
    @JsonIgnore
    var readBySupplier: Boolean = false
    @JsonIgnore
    var readByNicando: Boolean = false

    @Transient
    var read: Boolean = false
}

enum class EdiStatus {
    NEW,
    DEVELOPMENT,
    WAITING_FOR_CUSTOMER,
    WAITING_FOR_SUPPLIER,
    TESTING,
    DONE
}