package com.nicando.ediportal.payload

import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */
data class UserSummary(
        val id: Long,
        val username: String,
        val authorities:  MutableList<String>
)