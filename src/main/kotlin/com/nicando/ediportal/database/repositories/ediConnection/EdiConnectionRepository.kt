package com.nicando.ediportal.database.repositories.ediConnection

import com.nicando.ediportal.database.model.edi.EdiConnection
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

    fun findAllBySupplierIdOrCustomerId(supplier_id: Long, customer_id: Long, pageable: Pageable): Page<EdiConnection>

    fun findEdiConnectionsByCustomerIdOrSupplierId(customer_id: Long, supplier_id: Long): List<EdiConnection>
}