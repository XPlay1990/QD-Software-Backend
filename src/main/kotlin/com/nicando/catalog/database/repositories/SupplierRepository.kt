package com.nicando.catalog.database.repositories

import com.nicando.catalog.database.models.suppliergoods.Supplier
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
interface SupplierRepository : JpaRepository<Supplier, Long>