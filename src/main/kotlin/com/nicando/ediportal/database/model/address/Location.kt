package com.nicando.ediportal.database.model.address

import com.nicando.ediportal.database.model.organization.Organization
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Location(

        @OneToOne
        val address: Address,

        val type: LocationType,

        @ManyToOne(fetch = FetchType.LAZY)
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

enum class LocationType {
    PLANT, HEADQUARTER
}