package com.nicando.catalog.database.models.suppliergoods

import java.net.URL
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */

@Entity
data class Supplier(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(nullable = false)
        val name: String,

        val supplierNumber: String,

        val catalogURL: URL,

        @OneToMany(fetch = FetchType.EAGER)
        val articles: List<Article>
)