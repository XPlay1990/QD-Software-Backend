package com.qd.portal.user.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.qd.portal.user.rest.login.JwtAuthenticationResponse
import com.qd.portal.security.JwtTokenProvider
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @author : j_ada
 * @since : 10.08.2019, Sa.
 **/
@Service
@PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_PREVIOUS_ADMINISTRATOR')")
class SwitchUserService(private val jwtTokenProvider: JwtTokenProvider) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val jwt = jwtTokenProvider.generateToken(authentication)
        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON.toString()

        val mapper = ObjectMapper()
        response.writer.write(mapper.writeValueAsString(JwtAuthenticationResponse(jwt)))
    }
//    public override fun attemptSwitchUser(request: HttpServletRequest): Authentication? {
//        val current = SecurityContextHolder.getContext().authentication
//        // Put here all the checkings and initialization you want to check before switching.
//
//        return super.attemptSwitchUser(request);
//    }
//
//    public override fun attemptExitUser(request: HttpServletRequest?): Authentication {
//        val current = SecurityContextHolder.getContext().authentication
//
//        // Checkings when switch back called.
//        return super.attemptExitUser(request)
//    }
}