package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.model.user.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/
@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): VerificationToken
}