package com.qd.portal.user.rest

import com.qd.portal.user.service.AuthenticationInfoService
import com.qd.portal.common.rest.apiResponse.ResponseMessage
import com.qd.portal.user.service.UserService
import com.qd.portal.user.database.model.Gender
import com.qd.portal.user.database.model.User
import com.qd.portal.user.rest.api.UserSummary
import com.qd.portal.security.CurrentUser
import com.qd.portal.security.UserPrincipal
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
class UserController(private val authenticationInfoService: AuthenticationInfoService,
                     private val userService: UserService) {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getAllUsers(): ResponseMessage {
        logger.info("Getting all users for User: ${authenticationInfoService.getUsernameFromAuthentication()}}")
        return ResponseMessage(true, userService.findAll())
    }

    @GetMapping
    fun getUserById(@RequestParam id: Long): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findUser(id))
    }

    @PostMapping("/{id}/password/reset")
    fun resetUserPassword(@CurrentUser currentUser: UserPrincipal, @PathVariable id: Long) {
        logger.info("Request for password reset from User: ${currentUser.username}")
//        user.resetPassword();
        TODO("not implemented")
    }

    @PostMapping("/{id}/password/change")
    fun changeUserPassword(@CurrentUser currentUser: UserPrincipal, @PathVariable id: Long) {
        logger.info("Request for password reset from User: ${currentUser.username}")
//        user.resetPassword();
        TODO("not implemented")
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
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

    @GetMapping("/genders")
    fun getAllGenders(): Array<Gender> {
        return Gender.values()
    }

    @GetMapping("/checkUsernameAvailability")
    fun checkUsernameAvailability(@RequestParam username:String): Boolean {
        return !userService.existsUserWithUsername(username)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}