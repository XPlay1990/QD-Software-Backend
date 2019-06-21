package com.nicando.ediportal.database.models.user

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val userName: String,
        val password: String,
        val email: String,

        @OneToMany(fetch = FetchType.LAZY)
        val roles: List<Role>,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn
        val organization: Organization
)