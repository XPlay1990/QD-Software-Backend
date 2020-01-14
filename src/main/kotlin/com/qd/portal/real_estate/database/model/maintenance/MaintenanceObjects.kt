package com.qd.portal.real_estate.database.model.maintenance

import com.fasterxml.jackson.annotation.JsonFormat
import com.qd.portal.user.database.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import javax.persistence.*

/**
 * @author : j_ada
 * @since : 14.01.2020, Di.
 **/
@Entity
data class MaintenanceObjects(
        var name: String,
        var type: String,
        var lastMaintenance: LocalDate,
        var maintenancePeriod: Period,

        @OneToMany
        var plannedNextMaintenance: MutableList<PlannedMaintenance>?,

        @ManyToMany
        var possibleServiceProviderList: MutableList<User>?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @CreationTimestamp
    var creationTime: LocalDateTime? = null

    @JsonFormat(pattern = "dd-MM-yyyy (HH:mm)")
    @UpdateTimestamp
    var updateTime: LocalDateTime? = null
}