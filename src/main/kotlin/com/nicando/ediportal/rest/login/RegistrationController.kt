package com.nicando.ediportal.rest.login

import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.user.UserRegistrationService
import com.nicando.ediportal.common.user.UserService
import com.nicando.ediportal.logic.register.RegistrationRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/
@RestController
@RequestMapping("/registration")
class RegistrationController(private val userService: UserService, private val userRegistrationService: UserRegistrationService) {

    @PostMapping
    fun registerUser(@Valid @RequestBody registrationRequest: RegistrationRequest): ResponseEntity<*> {
        logger.info("Trying to register new User with username: ${registrationRequest.username}")
        if (userService.existsUserWithUsername(registrationRequest.username)) {
            return ResponseEntity(ResponseMessage(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }

        if (userService.existsByEmail(registrationRequest.email)) {
            return ResponseEntity(ResponseMessage(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)
        }

        userRegistrationService.registerUserAndCreateToken(registrationRequest)

        return ResponseEntity.ok().body<Any>(ResponseMessage(true, "User registered successfully"))
    }

    @GetMapping
    fun prepareActivationByToken(@RequestParam verificationToken: String): String {
        return userRegistrationService.getUsernameByToken(verificationToken)
    }

    @PutMapping
    fun activateUserByToken(@RequestBody password: String) {

    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}