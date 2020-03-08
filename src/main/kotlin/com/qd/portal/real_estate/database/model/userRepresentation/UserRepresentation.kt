package com.qd.portal.real_estate.database.model.userRepresentation

import com.fasterxml.jackson.annotation.JsonFormat
import com.qd.portal.organization.database.model.Organization
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author : j_ada
 * @since : 14.01.2020, Di.
 **/
@Entity
class UserRepresentation(
        // Can be a persisted representation of a registered user,
        // or some extern contacts that are not part of the system
        var name: String,
        var email: String,
        @ManyToOne
        var organization: Organization?
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