package com.nicando.ediportal.user.database.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nicando.ediportal.organization.database.model.Organization
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class User(
        @Column(unique = true, length = 40)
        @NotBlank
        @Size(max = 40)
        var username: String,

        @NotBlank
        @Size(max = 40)
        @Email
        var email: String,

        var firstName: String,
        var lastName: String,

        @ManyToOne
        @JoinColumn
        var organization: Organization,

        @Enumerated(EnumType.STRING)
        var gender: Gender
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    val creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null

    @JsonIgnore
    @ManyToMany
    var roles: MutableSet<Role> = mutableSetOf()

    var locale: Locale = Locale.ENGLISH

    var isActive: Boolean = false

    @NotBlank
    @Size(min = 6, max = 100)
    @JsonIgnore
    var password: String = "NOT_ACTIVATED"
}

enum class Gender {
    MALE,
    FEMALE,
    DIVERS
}