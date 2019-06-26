package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByUsernameOrEmail(username: String, email: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}