package com.qd.portal.user.database.repository

import com.qd.portal.user.database.model.Role
import com.qd.portal.user.database.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role
}