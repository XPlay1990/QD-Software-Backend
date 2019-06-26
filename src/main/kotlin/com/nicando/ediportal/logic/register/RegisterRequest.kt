package com.nicando.ediportal.logic.register

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
class RegisterRequest(
        @NotBlank
        @Size(min = 3, max = 15)
        var username: String,

        @NotBlank
        @Size(max = 40)
        @Email
        var email: String,

        @NotBlank
        @Size(min = 6, max = 20)
        var password: String,

        @NotBlank
        @Size(min = 4, max = 40)
        var firstName: String,

        @NotBlank
        @Size(min = 4, max = 40)
        var lastName: String
)