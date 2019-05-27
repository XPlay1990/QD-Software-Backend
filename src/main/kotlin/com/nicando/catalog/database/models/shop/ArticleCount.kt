package com.nicando.catalog.database.models.shop

import com.nicando.catalog.database.models.suppliergoods.Article
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class ArticleCount(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToOne
        val article: Article,
        val count: Int
)