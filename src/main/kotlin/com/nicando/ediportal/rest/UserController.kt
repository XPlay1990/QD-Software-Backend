package com.nicando.ediportal.rest

import com.nicando.ediportal.database.models.user.Role
import com.nicando.ediportal.database.models.user.RoleName
import com.nicando.ediportal.database.models.user.User
import com.nicando.ediportal.database.repositories.OrgRepository
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
class UserController(private val userRepository: UserRepository, private val orgRepository: OrgRepository, private val roleRepository: RoleRepository) {
    var LOGGER = LoggerFactory.getLogger(this.javaClass)


    @GetMapping("/all")
    fun getAllUsers(): List<User> = userRepository.findAll()

    @GetMapping
    fun getUserById(@RequestParam id:Long): ResponseEntity<User> {
        return userRepository.findById(id).map { user ->
            ResponseEntity.ok(user)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createDummyUser(): ResponseEntity<User> {
        val adminRole = Role(0, RoleName.ADMIN)
        roleRepository.save(adminRole)
        val user = User(0, "Jan", "test",
                "j.adamczyk@nicando.com", roleRepository.findAll(), orgRepository.findById(1).get()
        )
        userRepository.save(user)
        return ResponseEntity.ok(user)
    }
}