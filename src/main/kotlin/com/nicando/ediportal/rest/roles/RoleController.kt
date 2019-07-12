package com.nicando.ediportal.rest.roles

import com.nicando.ediportal.logic.roles.RoleAssignerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */

@PreAuthorize("hasRole('ROLE_ADMIN')") //TODO: Role_assigner role?
@RestController
@RequestMapping("/addrole")
class RoleController(private val roleAssignerService: RoleAssignerService) {
    var LOGGER = LoggerFactory.getLogger(this.javaClass)


    @PutMapping
    fun assignRoleToUser(@RequestParam userId: Long, @RequestParam roleId: Long): ResponseEntity<String> {
        LOGGER.info("Invoked assignRole Role: $roleId, User: $userId")
        try {
            roleAssignerService.assignRoleToUser(userId, roleId)
        } catch (e: IllegalStateException) {
            return ResponseEntity.badRequest().body(e.message)
        }
        return ResponseEntity.ok("Successfully assigned Role $roleId to user $userId!")
    }
}