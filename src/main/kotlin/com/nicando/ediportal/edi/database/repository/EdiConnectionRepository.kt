package com.nicando.ediportal.edi.database.repository

import com.nicando.ediportal.edi.database.model.EdiConnection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Repository
interface EdiConnectionRepository : JpaRepository<EdiConnection, Long> {
    fun findEdiConnectionsByCustomerId(customerId: Long)
    fun findEdiConnectionsBySupplierId(supplierId: Long)

    fun findEdiConnectionsByCustomerName(customer_name: String)
    fun findEdiConnectionsBySupplierName(supplier_name: String)

    fun findEdiConnectionsByCustomerIdOrSupplierId(customer_id: Long, supplier_id: Long): List<EdiConnection>

    //React Listing
    fun findAllBySupplierIdOrCustomerId(supplier_id: Long, customer_id: Long, pageable: Pageable): Page<EdiConnection>
}