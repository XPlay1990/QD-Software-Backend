package com.nicando.ediportal.user.database.repository

import com.nicando.ediportal.user.database.model.Role
import com.nicando.ediportal.user.database.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role
}