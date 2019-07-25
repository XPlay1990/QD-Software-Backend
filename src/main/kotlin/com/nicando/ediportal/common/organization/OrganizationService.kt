package com.nicando.ediportal.common.organization

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.repositories.OrganizationRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class OrganizationService(private val authenticationInfoService: AuthenticationInfoService,
                          private val organizationRepository: OrganizationRepository) {

    fun findOrganizationById(id: Long): Optional<Organization> {
        return organizationRepository.findById(id)
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findAllCustomerOrgs(): List<Organization> {
        logger.info("Getting all Customer-organizations for user: ${authenticationInfoService.getUsernameFromAuthentication()}")
        return organizationRepository.findAllByIsCustomerTrueAndIsActiveTrueOrderByName()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)

        @Value("\$app.Constants.PageResponse.MAX_SIZE")
        private val MAX_PAGE_SIZE: Int = 50
    }
}