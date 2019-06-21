package com.nicando.ediportal.database.model.edi.message

import com.nicando.ediportal.database.model.user.User
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
open class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        open var id: Long,

        @ManyToOne
        open var sender: User,
        open var subject: String,
        open var text: String,
        open var readByCustomer: Boolean,
        open var readBySupplier: Boolean,
        open var readByNicando: Boolean
)