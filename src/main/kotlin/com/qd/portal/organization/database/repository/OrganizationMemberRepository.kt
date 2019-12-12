package com.qd.portal.organization.database.repository

import com.qd.portal.user.database.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 29.07.2019, Mo.
 **/
@Repository
interface OrganizationMemberRepository : JpaRepository<User, Long> {
    fun findAllByOrganizationId(organization_id: Long): List<User>
    fun findAllByOrganizationName(organization_name: String): List<User>
}