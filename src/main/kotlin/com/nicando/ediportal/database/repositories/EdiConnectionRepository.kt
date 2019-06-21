package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.model.edi.EdiConnection
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
interface EdiConnectionRepository : JpaRepository<EdiConnection, Long> {
    fun findEdiConnectionsByCustomerId(customerId: Long)
    fun findEdiConnectionsBySupplierId(supplierId: Long)

    fun findEdiConnectionsByCustomerName(customer_name: String)
    fun findEdiConnectionsBySupplierName(supplier_name: String)
}