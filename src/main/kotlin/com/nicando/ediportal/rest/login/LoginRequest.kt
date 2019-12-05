package com.nicando.ediportal.rest.login

import javax.validation.constraints.NotBlank

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
class LoginRequest(
        @NotBlank
        val usernameOrEmail: String,

        @NotBlank
        val password: String
)