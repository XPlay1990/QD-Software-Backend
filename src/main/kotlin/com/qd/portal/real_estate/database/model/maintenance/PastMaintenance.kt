package com.qd.portal.real_estate.database.model.maintenance

import com.fasterxml.jackson.annotation.JsonFormat
import com.qd.portal.edi.database.model.Attachment
import com.qd.portal.organization.database.model.Organization
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author : j_ada
 * @since : 14.01.2020, Di.
 **/
@Entity
class PastMaintenance(
        var date: LocalDate,
        var costs: Int,

        var serviceProviderName: String,
        var serviceProviderEmail: String,
        @ManyToOne
        var serviceProviderOrganization: Organization?
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

    @OneToMany
    var attachmentList: MutableList<Attachment> = mutableListOf()
}