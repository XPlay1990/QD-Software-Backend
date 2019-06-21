package com.nicando.ediportal.database.model.user

import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.model.role.Role
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class User(

        val userName: String,
        val password: String,
        val email: String,

        @OneToMany
        val roles: List<Role>,

        @ManyToOne
        @JoinColumn
        val organization: Organization
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}