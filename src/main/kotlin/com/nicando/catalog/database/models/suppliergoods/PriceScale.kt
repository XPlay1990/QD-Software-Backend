package com.nicando.catalog.database.models.suppliergoods

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */

@Entity
data class PriceScale(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val minQuantity: BigDecimal,
        val unitPrice: BigDecimal
)