package com.nicando.catalog.database.models.suppliergoods

import java.math.BigDecimal
import javax.persistence.*


/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Entity
data class Article(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val description: String,

        val singleUnitPrice: BigDecimal,

        val priceUnit: String,

        @OneToMany(fetch = FetchType.EAGER)
        val priceList: List<PriceScale>
)
