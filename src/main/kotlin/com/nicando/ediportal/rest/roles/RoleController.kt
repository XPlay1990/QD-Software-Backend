package com.nicando.ediportal.rest.roles

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.logic.roles.RoleService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */

@PreAuthorize("hasRole('ADMIN')") //TODO: Role_assigner role?
@RestController
@RequestMapping("/addrole")
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

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}