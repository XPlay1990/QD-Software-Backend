package com.qd.portal.edi.database.repository

import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.excel.EdiConnectionProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@Repository
interface EdiConnectionRepository : JpaRepository<EdiConnection, Long>, JpaSpecificationExecutor<EdiConnection> {
    fun findEdiConnectionsByCustomerId(customerId: Long)
    fun findEdiConnectionsBySupplierId(supplierId: Long)

    fun findEdiConnectionsByCustomerName(customer_name: String)
    fun findEdiConnectionsBySupplierName(supplier_name: String)

    fun findEdiConnectionsByCustomerIdOrSupplierId(customer_id: Long, supplier_id: Long): List<EdiConnection>

    //React Listing
    fun findAllBySupplierIdOrCustomerId(supplier_id: Long, customer_id: Long, pageable: Pageable): Page<EdiConnection>

    fun findAllBySupplierIdOrCustomerId(supplier_id: Long, customer_id: Long): MutableList<EdiConnection>

    @Query("Select customer.name as customer, supplier.name as supplier, status as status, " +
            "updateTime as updateTime, assignedDeveloper as dev " +
            "from EdiConnection")
    fun findAllForExcel(): List<EdiConnectionProjection> // not working, ignores if dev is null
}