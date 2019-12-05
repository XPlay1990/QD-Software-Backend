package com.nicando.ediportal.database.model.user

import java.sql.Timestamp
import java.util.*
import javax.persistence.*


/**
 * @author : j_ada
 * @since : 05.12.2019, Do.
 **/
@Entity
data class VerificationToken(
        private val token: String,
        private val expiryDate: Date,

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(nullable = false)
        private val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long = 0

    private fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = Timestamp(cal.time.time)
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }

    companion object {
        private const val EXPIRATION = 60 * 24
    }
}