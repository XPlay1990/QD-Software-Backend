package com.qd.portal.user.rest.api

import com.qd.portal.user.database.model.Gender
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
class RegistrationRequest(
        @NotBlank
        @Size(min = 3, max = 15)
        val username: String,

        @NotBlank
        val roles: MutableList<String>,

        @NotBlank
        @Size(max = 40)
        @Email
        val email: String,

        @NotBlank
        @Size(min = 4, max = 40)
        val firstName: String,

        @NotBlank
        @Size(min = 4, max = 40)
        val lastName: String,

        val organizationId: Long,

        val gender: Gender,

        val language: String
)