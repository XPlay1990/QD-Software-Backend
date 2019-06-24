package com.nicando.ediportal.rest.login

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@RestController
@RequestMapping("/login")
class LoginController {
    @GetMapping
    fun login(@RequestParam username: String, password: String) {

    }
}