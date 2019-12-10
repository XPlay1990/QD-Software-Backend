package com.nicando.ediportal.user.database.repository

import com.nicando.ediportal.user.database.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByIdAndIsActiveTrue(id: Long): Optional<User>
    fun findByUsername(username: String): User?
    fun findByIsActiveTrueAndUsernameOrEmail(username: String, email: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}