package com.nicando.ediportal.rest

import com.nicando.ediportal.database.model.role.Role
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.repositories.OrganizationRepository
import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */


@RestController
@RequestMapping("/user")
class UserController(private val userRepository: UserRepository, private val organizationRepository: OrganizationRepository,
                     private val roleRepository: RoleRepository) {
    var LOGGER = LoggerFactory.getLogger(this.javaClass)


    @GetMapping("/all")
    fun getAllUsers(): List<User> = userRepository.findAll()

    @GetMapping
    fun getUserById(@RequestParam id: Long): ResponseEntity<User> {
        return userRepository.findById(id).map { user ->
            ResponseEntity.ok(user)
        }.orElse(ResponseEntity.notFound().build())
    }

    //    @PostMapping
//    fun createUser(): ResponseEntity<User> {
//        userRepository.save(user)
//        return ResponseEntity.ok(user)
//    }
    @PostMapping("/{id}/password/reset")
    fun resetUserPassword(@PathVariable id: Long) {
        val user = userRepository.findById(id)
//        user.resetPassword();
        TODO("not implemented")
    }
}