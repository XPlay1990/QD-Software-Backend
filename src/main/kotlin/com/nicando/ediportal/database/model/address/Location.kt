package com.nicando.ediportal.database.model.address

import com.nicando.ediportal.database.model.organization.Organization
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Location(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToOne
        val address: Address,

        val type: LocationType,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn
        val organization: Organization
)

enum class LocationType {
    PLANT, HEADQUARTER
}