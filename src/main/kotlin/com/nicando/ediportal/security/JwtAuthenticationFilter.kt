package com.nicando.ediportal.security

import com.nicando.ediportal.exceptions.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Created by Jan Adamczyk on 25.06.2019.
 */
@Component
class JwtAuthenticationFilter(private val tokenProvider: JwtTokenProvider, private val customUserDetailsService: CustomUserDetailsService)
    : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//        try {
        val jwt = getJwtFromRequest(request)
        if (jwt != null) {
            if (tokenProvider.validateToken(jwt)) {
                val userId = tokenProvider.getUserIdFromJWT(jwt)

                val userDetails = customUserDetailsService.loadUserById(userId)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
//        } catch (ex: Exception) {
//            logger.error("Could not set user authentication in security context", ex)
//        }
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }
}