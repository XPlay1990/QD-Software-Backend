package com.nicando.ediportal.database.model.edi.message

import com.nicando.ediportal.database.model.user.User
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class TextMessage(
        override var id: Long,
        override var sender: User,
        override var subject: String,
        override var text: String,
        override var readByCustomer: Boolean,
        override var readBySupplier: Boolean,
        override var readByNicando: Boolean
) : Message(id, sender, subject, text, readByCustomer, readBySupplier, readByNicando)