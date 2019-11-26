package com.nicando.ediportal.rest

import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.user.UserService
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.payload.UserSummary
import com.nicando.ediportal.security.CurrentUser
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.*

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */


@RestController
@RequestMapping("/user")
class UserController(private val userRepository: UserRepository,
                     private val userService: UserService) {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getAllUsers(): ResponseMessage = ResponseMessage(true, userRepository.findAll())

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
    fun resetUserPassword(@CurrentUser currentUser: UserPrincipal, @PathVariable id: Long) {
        logger.info("Request for password reset from User: ${currentUser.username}")
//        user.resetPassword();
        TODO("not implemented")
    }

    @GetMapping("/me")
//    @PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): ResponseMessage {
//        val roleStringList = mutableListOf<String>()
//        currentUser.authorities.forEach { authority: SimpleGrantedAuthority ->
//            roleStringList.add(authority.authority)
//        }
//        return UserSummary(currentUser.id, currentUser.username, roleStringList)
        return ResponseMessage(true, userService.findUser(currentUser.id))
    }

    @GetMapping("/me/withroles")
    fun getCurrentUserWithRoles(@CurrentUser currentUser: UserPrincipal): UserSummary {
        val roleStringList = mutableListOf<String>()
        currentUser.authorities.forEach { authority: SimpleGrantedAuthority ->
            roleStringList.add(authority.authority)
        }
        return UserSummary(currentUser.id, currentUser.username, roleStringList)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}