package com.nicando.ediportal.security.service

import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.exceptions.BadRequestException
import com.nicando.ediportal.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * Created by Jan Adamczyk on 02.07.2019.
 */
@Service
class AuthenticationInfoService(private val userRepository: UserRepository) {
    fun getUserIdFromAuthentication(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
                ?: throw BadRequestException("Token does not provide a valid User!")
    }

    fun getOrgIdFromAuthentication(): Long {
        val thisUser = userRepository.findById(getUserIdFromAuthentication()).get()
        return thisUser.organization!!.id
    }
}