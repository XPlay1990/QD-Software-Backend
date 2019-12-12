package com.qd.portal.user.rest

import com.qd.portal.user.service.AuthenticationInfoService
import com.qd.portal.user.database.model.RoleName
import com.qd.portal.user.service.roles.RoleService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */

@PreAuthorize("hasRole('ROLE_ADMIN')") //TODO: Role_assigner role?
@RestController
@RequestMapping("/role")
class RoleController(private val roleService: RoleService, private val authenticationInfoService: AuthenticationInfoService) {

    @PutMapping
    fun assignRoleToUser(@RequestParam userId: Long, @RequestParam roleId: Long): ResponseEntity<String> {
        logger.info(
                "User: ${authenticationInfoService.getUsernameFromAuthentication()} invoked assignRole Role: $roleId, User: $userId")

        try {
            roleService.assignRoleToUser(userId, roleId)
        } catch (e: IllegalStateException) {
            logger.warn(e.message)
            return ResponseEntity.badRequest().body(e.message)
        }

        val successMessage = "Successfully assigned Role $roleId to user $userId!"
        logger.info(successMessage)
        return ResponseEntity.ok(successMessage)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    fun getAllRoles(): Array<RoleName> {
        return roleService.getAllRoles()
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}