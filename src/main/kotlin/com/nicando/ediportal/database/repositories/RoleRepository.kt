package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.models.user.Role
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
interface RoleRepository : JpaRepository<Role, Long>