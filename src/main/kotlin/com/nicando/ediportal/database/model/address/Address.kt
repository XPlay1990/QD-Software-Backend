package com.nicando.ediportal.database.model.address

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@Entity
data class Address(

        val street: String,
        val houseNumber: Int,

        val city: String,
        val postcode: String,
        val country: String //TODO: Enumerize
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}