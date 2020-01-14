package com.qd.portal.edi.database.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qd.portal.organization.database.model.Organization
import javax.persistence.*

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
data class Attachment(
        var fileName: String,
        var fileType: String,

        var fileSize: Long,

        var uploaderName: String?,
        var uploaderEmail: String?,
        @ManyToOne
        var uploaderOrg: Organization?

//        @JsonIgnore
//        @Lob
//        var Attachment: ByteArray
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long = 0
}