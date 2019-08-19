package com.nicando.ediportal.database.model.edi.message

import com.nicando.ediportal.database.model.user.User
import javax.persistence.Entity

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class TextMessage(
        override var sender: User,
//        override var subject: String,
        override var text: String
) : Message(sender, text)