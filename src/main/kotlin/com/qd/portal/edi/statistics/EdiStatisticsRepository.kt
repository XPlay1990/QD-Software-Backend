package com.qd.portal.edi.statistics

import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.model.EdiStatus
import com.qd.portal.organization.database.model.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 01.01.2020, Mi.
 **/

@Repository
interface EdiStatisticsRepository : JpaRepository<EdiConnection, Long> {
    fun countDistinctByStatus(status: EdiStatus): Int
    fun countDistinctByCustomer(customer: Organization): Int

    fun countByStatusAndCustomerOrSupplier(status: EdiStatus, customer: Organization, supplier: Organization) : Int
}