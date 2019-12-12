package com.qd.portal.user.service.roles

import com.qd.portal.user.database.model.Role
import com.qd.portal.user.database.model.RoleName
import com.qd.portal.user.database.repository.RoleRepository
import com.qd.portal.user.database.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Jan Adamczyk on 12.07.2019.
 */
@Service
class RoleService(private val userRepository: UserRepository, private val roleRepository: RoleRepository) {

    @Transactional
    fun assignRoleToUser(userId: Long, roleId: Long) {
        val user = userRepository.findById(userId).get()
        val roleToAssign = roleRepository.findById(roleId).get()
        if (user.roles.contains(roleToAssign)) {
            throw IllegalStateException("Role already assigned!")
        } else {
            user.roles = mutableSetOf(roleToAssign)
        }
    }

    @Transactional
    fun findRoleByName(roleName: RoleName): Role {
//        return roleRepository.findByRoleName(roleName) ?: return roleRepository.save(Role(roleName))
        return roleRepository.findByRoleName(roleName)
    }

    fun getAllRoles(): Array<RoleName> {
        return RoleName.values()
    }
}