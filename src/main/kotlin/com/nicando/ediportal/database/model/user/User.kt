package com.nicando.ediportal.database.model.user

import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.model.role.Role
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
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

        @NotBlank
        @Size(min = 6, max = 100)
        var password: String,

        var firstName: String,
        var lastName: String,

        @OneToMany
        var roles: List<Role>?,

        @ManyToOne
        @JoinColumn
        var organization: Organization?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    val creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null

    var isActive: Boolean = true
}