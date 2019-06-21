package com.nicando.ediportal.database.model.role

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Role(
        val role: RoleName
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

enum class RoleName {
    ADMIN,
    EDI_READ,
    EDI_CREATE
}
