package com.nicando.catalog.database.models.shop

import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class Cart(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToMany(fetch = FetchType.EAGER)
        val articles: List<ArticleCount>
)