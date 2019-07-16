package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.model.role.Role
import com.nicando.ediportal.database.model.role.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role?
}