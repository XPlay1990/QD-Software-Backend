package com.nicando.ediportal.database.model.edi.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.nicando.ediportal.database.model.user.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class Message(
        @ManyToOne
        open var sender: User,
//        open var subject: String,
        open var text: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    open var id: Long = 0

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @CreationTimestamp
    open var creationTime: LocalDateTime? = null

//    @UpdateTimestamp
//    open var updateTime: LocalDateTime? = null

    open var readByCustomer: Boolean = false
    open var readBySupplier: Boolean = false
    open var readByNicando: Boolean = false
}