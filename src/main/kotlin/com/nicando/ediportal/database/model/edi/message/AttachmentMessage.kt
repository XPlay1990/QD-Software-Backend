package com.nicando.ediportal.database.model.edi.message

import com.nicando.ediportal.database.model.user.User
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

/**
 * Copyright (C) 2019-2019 Jan Adamczyk <j_adamczyk@hotmail.com>
 *
 * Created: 21.06.19
 * This file is part of ediportal
 *
 * This Code can not be copied and/or distributed without the express
 * permission of Jan Adamczyk
 */
@Entity
data class AttachmentMessage(
        override var sender: User,
        override var subject: String,
        override var text: String,

        @OneToMany(cascade = [CascadeType.ALL])
        var attachments: MutableSet<Attachment>
) : Message(sender, subject, text)