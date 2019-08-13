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
        val username: String,

        @NotBlank
        @Size(max = 40)
        @Email
        val email: String,

        @NotBlank
        @Size(min = 6, max = 20)
        val password: String,

        @NotBlank
        @Size(min = 4, max = 40)
        val firstName: String,

        @NotBlank
        @Size(min = 4, max = 40)
        val lastName: String,

        val organizationId: Long
)