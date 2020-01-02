package com.qd.portal.edi.statistics

import com.qd.portal.edi.database.model.EdiStatus
import com.qd.portal.organization.service.OrganizationService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author : j_ada
 * @since : 01.01.2020, Mi.
 **/
@Service
class EdiStatisticsService(private val statisticsRepository: EdiStatisticsRepository,
                           private val organizationService: OrganizationService) {
    fun getStatusStatistics(isAdmin: Boolean): MutableMap<EdiStatus, Int> {
        logger.info("Getting state Statistics")

        val statistics = mutableMapOf<EdiStatus, Int>()
        EdiStatus.values().forEach { status ->
            if (isAdmin) {
                statistics[status] = statisticsRepository.countDistinctByStatus(status)
            } else {
                val findOrganizationById = organizationService.findMyOrganization()

                statistics[status] = statisticsRepository.countByStatusAndCustomerOrSupplier(status, findOrganizationById, findOrganizationById)
            }
        }
        return statistics
    }

    fun getCustomerStatistics(isAdmin: Boolean): MutableMap<String, Int> {
        logger.info("Getting Customer Statistics")

        val statistics = mutableMapOf<String, Int>()
        if (isAdmin) {
            val allCustomerOrgs = organizationService.findAllCustomerOrgs()
            allCustomerOrgs.forEach { customer ->
                statistics[customer.name] = statisticsRepository.countDistinctByCustomer(customer)
            }
        } else {
            val myOrganization = organizationService.findMyOrganization()
            statistics[myOrganization.name] = statisticsRepository.countDistinctByCustomer(myOrganization)
        }
        return statistics
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}