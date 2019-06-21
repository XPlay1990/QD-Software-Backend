package com.nicando.ediportal.database.models.user

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Location(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val type: LocationType,

        val street: String,
        val houseNumber: Int,

        val city: String,
        val postcode: String,
        val country: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn
        val organization: Organization
)

enum class LocationType {
    PLANT, HEADQUARTER
}