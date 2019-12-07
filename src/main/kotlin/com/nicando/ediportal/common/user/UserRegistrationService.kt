package com.nicando.ediportal.common.user

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/

import com.nicando.ediportal.common.ServerService
import com.nicando.ediportal.common.exceptions.registration.RegistrationTokenExpiredException
import com.nicando.ediportal.common.exceptions.rest.BadRequestException
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.model.user.VerificationToken
import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.VerificationTokenRepository
import com.nicando.ediportal.database.repositories.organization.OrganizationRepository
import com.nicando.ediportal.logic.register.RegistrationRequest
import com.nicando.ediportal.mail.EmailServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UserRegistrationService(private val organizationRepository: OrganizationRepository,
                              private val roleRepository: RoleRepository,
                              private val verificationTokenRepository: VerificationTokenRepository,
                              private val userRepository: UserRepository,
                              private val emailServiceImpl: EmailServiceImpl,
                              private val passwordEncoder: PasswordEncoder,
                              private val serverService: ServerService) {

    fun getUsernameByToken(token: String): String {
        val registrationToken = verificationTokenRepository.findByToken(token)
                ?: throw RegistrationTokenExpiredException("Token already expired!")
        return registrationToken.user.username
    }

    @Transactional
    fun activateUser(password: String, token: String) {
        val verificationToken = verificationTokenRepository.findByToken(token)
                ?: throw RegistrationTokenExpiredException("Token already expired!")
        if (verificationToken.isExpired()) {
            //TODO: dont throw exception, return something useful
            throw RegistrationTokenExpiredException("Token already expired! Please contact the Nicando Support.")
        }

        val user = verificationToken.user
        user.isActive = true
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)

        verificationTokenRepository.delete(verificationToken)
    }

    @Transactional
    fun registerUserAndCreateToken(registrationRequest: RegistrationRequest) {
        val user = buildUserFromRegistrationRequest(registrationRequest)
        val verificationToken = VerificationToken(UUID.randomUUID().toString(), user)
        verificationTokenRepository.save(verificationToken)

        emailServiceImpl.sendEmailWithTemplate(user.email, "Welcome to the Nicando Edi-Portal!", "mail/registration/registrationToken",
                createRegisterUserContext(verificationToken), user.locale)
    }

    private fun createRegisterUserContext(verificationToken: VerificationToken): MutableMap<String, String> {
        val user = verificationToken.user
        return mutableMapOf("FirstName" to user.firstName, "LastName" to user.lastName, "UserName" to user.username,
                "Gender" to user.gender.name,
                "VerificationToken" to verificationToken.token, "ExpirationHours" to verificationToken.getExpirationHours().toString())
    }

    private fun buildUserFromRegistrationRequest(registrationRequest: RegistrationRequest): User {
        val user = User(registrationRequest.username,
                registrationRequest.email, registrationRequest.firstName, registrationRequest.lastName,
                organizationRepository.findById(registrationRequest.organizationId).get(), registrationRequest.gender)

        user.locale = Locale(registrationRequest.language)

        val userRole = roleRepository.findByRoleName(RoleName.ROLE_REGISTERED_USER)
                ?: throw BadRequestException("User Role not set.")

        user.roles = mutableSetOf(userRole)
        return user
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}