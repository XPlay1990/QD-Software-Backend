package com.qd.portal.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


/**
 * Created by Jan Adamczyk on 25.06.2019.
 */
@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(httpServletRequest: HttpServletRequest,
                          httpServletResponse: HttpServletResponse,
                          e: AuthenticationException) {
        logger.warn("Unauthorized request at: {}", httpServletRequest.requestURI)
        logger.warn("Responding with unauthorized error. Message - {}", e.message)
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}