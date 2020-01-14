package com.qd.portal.real_estate.database.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.qd.portal.real_estate.database.model.maintenance.MaintenanceObjects
import com.qd.portal.user.database.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author : j_ada
 * @since : 14.01.2020, Di.
 **/
@Entity
data class Building(
        var name: String,

        @ManyToOne
        var address: Address,

        @ManyToMany
        var renterList: MutableList<User>?,

        @ManyToMany
        var ownerList: MutableList<User>?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany
    var rentalProperties: MutableList<RentalProperty> = mutableListOf()

    @OneToMany
    var maintenanceObjects: MutableList<MaintenanceObjects> = mutableListOf()

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}