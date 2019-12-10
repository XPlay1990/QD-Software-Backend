package com.nicando.ediportal.user.rest.api

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */
data class UserSummary(
        val id: Long,
        val username: String,
        val authorities: MutableList<String>
)