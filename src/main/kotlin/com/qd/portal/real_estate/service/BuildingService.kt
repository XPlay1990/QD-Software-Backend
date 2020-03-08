package com.qd.portal.real_estate.service

import com.qd.portal.edi.database.repository.EdiConnectionRepository
import com.qd.portal.organization.database.repository.OrganizationRepository
import com.qd.portal.real_estate.database.model.Building
import com.qd.portal.real_estate.database.repository.BuildingRepository
import org.springframework.stereotype.Service

/**
 * @author : j_ada
 * @since : 15.01.2020, Mi.
 **/

@Service
class BuildingService(private val buildingRepository: BuildingRepository,
                      private val organizationRepository: OrganizationRepository) {
    fun addBuilding(building: Building, organizationId: Long) {
        val organization = organizationRepository.findById(organizationId).get()
        building.administrativeOrganization = organization
        buildingRepository.save(building)
    }
}