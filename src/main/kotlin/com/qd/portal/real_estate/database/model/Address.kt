package com.qd.portal.real_estate.database.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author : j_ada
 * @since : 14.01.2020, Di.
 **/
@Entity
data class Address(
        val street: String,
        val houseNumber: String,

        val city: String,
        val postcode: String,

        val country: String //TODO: Enumerize
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}