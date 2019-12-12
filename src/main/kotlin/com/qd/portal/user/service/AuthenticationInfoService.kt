package com.qd.portal.user.service

import com.qd.portal.user.database.repository.UserRepository
import com.qd.portal.common.exceptions.rest.BadRequestException
import com.qd.portal.security.UserPrincipal
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
        return thisUser.organization.id
    }

    fun getOrgNameFromAuthentication(): String {
        val thisUser = userRepository.findById(getUserIdFromAuthentication()).get()
        return thisUser.organization.name
    }

    fun getUsernameFromAuthentication(): String {
        return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).username
    }
}