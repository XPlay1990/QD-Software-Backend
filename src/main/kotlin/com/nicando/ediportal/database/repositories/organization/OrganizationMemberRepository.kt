package com.nicando.ediportal.database.repositories.organization

import com.nicando.ediportal.database.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 29.07.2019, Mo.
 **/
@Repository
interface OrganizationMemberRepository : JpaRepository<User, Long> {
    fun findAllByOrganizationId(organization_id: Long): List<User>
}