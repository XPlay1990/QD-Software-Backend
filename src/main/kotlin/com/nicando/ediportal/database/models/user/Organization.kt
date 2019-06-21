package com.nicando.ediportal.database.models.user

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class Organization(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val name: String,
        val identifierKey: Int,

        val fallBackEmail: String,

        @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val members: List<User>?,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val address : Address?,

        @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val locations: List<Location>?
)