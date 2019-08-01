package com.nicando.ediportal.database.repositories.organization

import com.nicando.ediportal.database.model.organization.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Repository
interface OrganizationRepository : JpaRepository<Organization, Long> {
    fun findAllByIsCustomerTrueAndIsActiveTrueOrderByName(): List<Organization>
    fun findAllByIsCustomerFalseAndIsActiveTrueOrderByName(): List<Organization>

    //TODO: those functions are only for TEST-Controller
    fun findOrganizationsByNameLike(name: String): List<Organization>

    fun findOrganizationsByNameNotLike(name: String): List<Organization>
}