package com.nicando.ediportal.user.database.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*


/**
 * @author : j_ada
 * @since : 05.12.2019, Do.
 **/
@Entity
data class VerificationToken(
        val token: String,

        @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
        @JoinColumn(nullable = false)
        val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long = 0

    private val expiryDate: Date = calculateExpiryDate()

    fun isExpired(): Boolean {
        return (expiryDate < Date())
    }

    fun getExpirationHours(): Int {
        return EXPIRATION_HOURS
    }

    private fun calculateExpiryDate(): Date {
        val cal = Calendar.getInstance()
        cal.time = Timestamp(cal.time.time)
        cal.add(Calendar.MINUTE, EXPIRATION)
        return Date(cal.time.time)
    }

    companion object {
        private const val EXPIRATION_HOURS = 48
        private const val EXPIRATION = 60 * EXPIRATION_HOURS
    }
}