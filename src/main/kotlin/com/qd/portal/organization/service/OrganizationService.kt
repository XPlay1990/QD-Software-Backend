package com.qd.portal.organization.service

import com.qd.portal.user.service.AuthenticationInfoService
import com.qd.portal.organization.database.model.Organization
import com.qd.portal.user.database.model.User
import com.qd.portal.organization.database.repository.OrganizationMemberRepository
import com.qd.portal.organization.database.repository.OrganizationRepository
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class OrganizationService(private val authenticationInfoService: AuthenticationInfoService,
                          private val organizationRepository: OrganizationRepository,
                          private val organizationMemberRepository: OrganizationMemberRepository) {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findOrganizationById(id: Long): Optional<Organization> {
        return organizationRepository.findById(id)
    }

    fun findAllDevelopers(): List<User>? {
        logger.info("Getting all Developers for User: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationMemberRepository.findAllByOrganizationName("QD Software")
    }

    fun findAllOrganizationMembers(id: Long): List<User>? {
        logger.info("Getting all members from Organization with Id $id for User: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationMemberRepository.findAllByOrganizationId(id)
    }

    fun findAllCustomerOrgs(): List<Organization> {
        logger.info("Getting all Customer-organizations for user: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationRepository.findAllByIsCustomerTrueAndIsActiveTrueOrderByName()
    }

    fun findAllSupplierOrgs(): List<Organization> {
        logger.info("Getting all Customer-organizations for user: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationRepository.findAllByIsCustomerFalseAndIsActiveTrueOrderByName()
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findAllOrgs(): List<Organization> {
        logger.info("Getting all organizations for user: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationRepository.findAll()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}