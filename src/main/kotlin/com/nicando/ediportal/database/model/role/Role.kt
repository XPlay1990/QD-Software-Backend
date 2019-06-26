package com.nicando.ediportal.database.model.role

import org.hibernate.annotations.NaturalId
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Role(
        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        val roleName: RoleName
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

enum class RoleName {
    ROLE_NONE,
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_EDI_READ,
    ROLE_EDI_CREATE
}
