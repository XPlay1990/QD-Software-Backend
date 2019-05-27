package com.nicando.catalog.database.models.buyer

import com.nicando.catalog.database.models.shop.Cart
import javax.persistence.*

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class Buyer(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val userName: String,
        val password: String,
        val email: String,

//        @OneToMany
//        val roles:List<Role>

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn
        val organization: Organization,

        @OneToOne
        val cart: Cart
)