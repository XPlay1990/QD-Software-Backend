package com.qd.portal.edi.database.model.message

import com.qd.portal.user.database.model.User
import javax.persistence.Entity

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Entity
data class TextMessage(
        override var sender: User,
        override var text: String
) : Message(sender, text)