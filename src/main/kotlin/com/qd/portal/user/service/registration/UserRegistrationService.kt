package com.qd.portal.user.service.registration

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/

import com.qd.portal.server.service.ServerService
import com.qd.portal.common.exceptions.registration.RegistrationTokenExpiredException
import com.qd.portal.user.database.model.Role
import com.qd.portal.user.database.model.RoleName
import com.qd.portal.user.database.model.User
import com.qd.portal.user.database.model.VerificationToken
import com.qd.portal.user.database.repository.RoleRepository
import com.qd.portal.user.database.repository.UserRepository
import com.qd.portal.user.database.repository.VerificationTokenRepository
import com.qd.portal.organization.database.repository.OrganizationRepository
import com.qd.portal.mail.EmailServiceImpl
import com.qd.portal.user.rest.api.RegistrationRequest
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

        val registeredUserRole = roleRepository.findByRoleName(RoleName.ROLE_REGISTERED_USER)

        val addedRolesList = mutableSetOf<Role>()
        registrationRequest.roles.forEach { roleNameString ->
            val role = roleRepository.findByRoleName(RoleName.valueOf(roleNameString))
            addedRolesList.add(role)
        }
        if (!addedRolesList.contains(registeredUserRole)) {
            addedRolesList.add(registeredUserRole)
        }

        user.roles = addedRolesList
        return user
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}