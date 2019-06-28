package com.nicando.ediportal.rest.adminFunctions

import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.security.JwtTokenProvider
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
class AdminFunctionsController(private val authenticationManager: AuthenticationManager, private val jwtTokenProvider: JwtTokenProvider,
                               private val userRepository: UserRepository, private val roleRepository: RoleRepository,
                               private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/switch/{userId}")
    fun switchToUser(@PathVariable userId: Long) {
//        jwtTokenProvider.generateToken()
        TODO("not implemented")
    }
}