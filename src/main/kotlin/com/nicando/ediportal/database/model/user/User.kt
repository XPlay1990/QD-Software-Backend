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

        var userName: String,
        var password: String,
        var email: String,

        @OneToMany
        var roles: List<Role>,

        @ManyToOne
        @JoinColumn
        var organization: Organization
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    val creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}