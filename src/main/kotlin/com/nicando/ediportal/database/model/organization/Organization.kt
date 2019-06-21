package com.nicando.ediportal.database.model.organization

import com.nicando.ediportal.database.model.address.Address
import com.nicando.ediportal.database.model.address.Location
import com.nicando.ediportal.database.model.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class Organization(

        val name: String,
        val identifierKey: Int,

        val fallBackEmail: String,

        @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val members: List<User>?,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val address: Address?,

        @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val locations: List<Location>?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}