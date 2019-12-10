package com.nicando.ediportal.user.service

import com.nicando.ediportal.server.service.ServerService
import com.nicando.ediportal.user.database.model.User
import com.nicando.ediportal.user.database.repository.RoleRepository
import com.nicando.ediportal.user.database.repository.UserRepository
import com.nicando.ediportal.user.database.repository.VerificationTokenRepository
import com.nicando.ediportal.organization.database.repository.OrganizationRepository
import com.nicando.ediportal.mail.EmailServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class UserService(private val authenticationInfoService: AuthenticationInfoService,
                  private val userRepository: UserRepository,
                  private val organizationRepository: OrganizationRepository,
                  private val roleRepository: RoleRepository,
                  private val verificationTokenRepository: VerificationTokenRepository,
                  private val emailServiceImpl: EmailServiceImpl,
                  private val passwordEncoder: PasswordEncoder,
                  private val serverService: ServerService) {

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    fun findUser(id: Long): User {
        logger.info("Getting User: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return userRepository.findById(id).get()
    }

    fun existsUserWithUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}