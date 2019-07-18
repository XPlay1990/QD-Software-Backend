package com.nicando.ediportal.database.model.edi.message

import com.fasterxml.jackson.annotation.JsonIgnore
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

        @JsonIgnore
        @Lob
        var Attachment: ByteArray
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Attachment

        if (id != other.id) return false
        if (fileName != other.fileName) return false
        if (fileType != other.fileType) return false
        if (fileSize != other.fileSize) return false
        if (!Attachment.contentEquals(other.Attachment)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + fileType.hashCode()
        result = 31 * result + fileSize.hashCode()
        result = 31 * result + Attachment.contentHashCode()
        return result
    }
}