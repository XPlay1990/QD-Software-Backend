package com.nicando.ediportal.security

import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.common.exceptions.rest.ResourceNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        // Let people login with either username or email
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                ?: throw UsernameNotFoundException("User not found with username or email : $usernameOrEmail")

        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User", "id", id) }

        return UserPrincipal.create(user)
    }
}