package com.nicando.ediportal.common.admin

import com.nicando.ediportal.security.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter
import org.springframework.stereotype.Service


/**
 * @author : j_ada
 * @since : 10.08.2019, Sa.
 **/
@Service
@PreAuthorize("hasRole('ADMIN')||hasRole('PREVIOUS_ADMINISTRATOR')")
class AdminFunctionsService(private val customUserDetailsService: CustomUserDetailsService) {
//    public override fun attemptSwitchUser(request: HttpServletRequest): Authentication? {
//        val current = SecurityContextHolder.getContext().authentication
//
//        val switchUserFilter = SwitchUserFilter()
//        switchUserFilter.setUserDetailsService(customUserDetailsService)
//        switchUserFilter.
//        // Put here all the checkings and initialization you want to check before switching.
//        return super.attemptSwitchUser(request);
//    }
//
//
//    public override fun attemptExitUser(request: HttpServletRequest?): Authentication {
//        val current = SecurityContextHolder.getContext().authentication
//
//        // Checkings when switch back called.
//        return super.attemptExitUser(request)
//    }
}