package com.nicando.ediportal.common.user

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.organization.OrganizationMemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class UserService(private val authenticationInfoService: AuthenticationInfoService,
                  private val userRepository: UserRepository,
                  private val organizationMemberRepository: OrganizationMemberRepository) {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findUser(id: Long): User {
        logger.info("Getting User: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return userRepository.findById(id).get()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}