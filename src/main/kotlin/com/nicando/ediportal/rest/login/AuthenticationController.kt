package com.nicando.ediportal.rest.login

import com.nicando.ediportal.common.apiResponse.register.RegisterResponse
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.exceptions.ServerException
import com.nicando.ediportal.logic.register.RegisterRequest
import com.nicando.ediportal.security.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@RestController
@RequestMapping("/auth")
class AuthenticationController(private val authenticationManager: AuthenticationManager, private val jwtTokenProvider: JwtTokenProvider,
                               private val userRepository: UserRepository, private val roleRepository: RoleRepository,
                               private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.usernameOrEmail,
                        loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = jwtTokenProvider.generateToken(authentication)
        return ResponseEntity.ok<Any>(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(registerRequest.username)) {
            return ResponseEntity(RegisterResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }

        if (userRepository.existsByEmail(registerRequest.email)) {
            return ResponseEntity(RegisterResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)
        }

        // Creating user's account
        val user = User(registerRequest.username,
                registerRequest.email, registerRequest.password, registerRequest.firstName, registerRequest.lastName, null)

        user.password = passwordEncoder.encode(user.password)

        val userRole = roleRepository.findByRoleName(RoleName.ROLE_REGISTERED_USER)
                ?: throw ServerException("User Role not set.")

        user.roles = mutableListOf(userRole)

        val result = userRepository.save(user)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.username).toUri()

        return ResponseEntity.created(location).body<Any>(RegisterResponse(true, "User registered successfully"))
    }
}