package com.nicando.ediportal.organization.database.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nicando.ediportal.organization.database.model.address.Address
import com.nicando.ediportal.organization.database.model.address.Location
import com.nicando.ediportal.user.database.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class Organization(

        @NaturalId
        val name: String,
        val identifierKey: Int,

        val fallBackEmail: String,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val address: Address?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @JsonIgnore
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var members: List<User>? = null

    @JsonIgnore
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var locations: List<Location>? = null

    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null

    var isCustomer: Boolean = false

    var isActive: Boolean = true
        set(boolean) {
            if (!boolean) {
                //deactivate members if organization is set to inactive
                members?.forEach { it.isActive = false }
            }
            field = boolean
        }
}