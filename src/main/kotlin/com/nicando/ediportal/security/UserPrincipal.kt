package com.nicando.ediportal.security

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
import com.fasterxml.jackson.annotation.JsonIgnore
import com.nicando.ediportal.database.model.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

data class UserPrincipal(val id: Long?, private val username: String,
                         @field:JsonIgnore val email: String, @field:JsonIgnore private val password: String,
                         private val authorities: MutableList<SimpleGrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {

        fun create(user: User): UserPrincipal {
            val authorities = user.roles.stream().map { role -> SimpleGrantedAuthority(role.roleName.name) }.collect(Collectors.toList())

            return UserPrincipal(
                    user.id,
//                    user.getName(),
                    user.username,
                    user.email,
                    user.password,
                    authorities
            )
        }
    }
}
