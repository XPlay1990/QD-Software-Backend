package com.nicando.ediportal.logic.roles

import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Jan Adamczyk on 12.07.2019.
 */
@Service
class RoleAssignerService(private val userRepository: UserRepository, private val roleRepository: RoleRepository) {

    @Transactional
    fun assignRoleToUser(userId: Long, roleId: Long) {
        val user = userRepository.findById(userId).get()
        val roleToAssign = roleRepository.findById(roleId).get()
        if (user.roles.contains(roleToAssign)) {
            throw IllegalStateException("Role already assigned!")
        } else {
            user.roles.add(roleToAssign)
        }
    }

}