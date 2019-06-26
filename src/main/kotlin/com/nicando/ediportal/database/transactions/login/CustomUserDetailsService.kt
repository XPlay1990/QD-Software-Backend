package com.nicando.ediportal.database.transactions.login

import com.nicando.ediportal.database.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.HashSet
import org.springframework.security.core.GrantedAuthority


/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userRepository.findByUsername(userName)

        val grantedAuthorities = HashSet<GrantedAuthority>()
        for (role in user!!.roles) {
            grantedAuthorities.add(SimpleGrantedAuthority(role.roleName.name))
        }

        return org.springframework.security.core.userdetails.User(user.username, user.password, grantedAuthorities)
    }
}