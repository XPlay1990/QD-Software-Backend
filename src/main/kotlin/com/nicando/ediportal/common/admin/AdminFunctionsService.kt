package com.nicando.ediportal.common.admin

import com.nicando.ediportal.security.CustomUserDetailsService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * @author : j_ada
 * @since : 10.08.2019, Sa.
 **/
@Service
@PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_PREVIOUS_ADMINISTRATOR')")
class AdminFunctionsService(private val customUserDetailsService: CustomUserDetailsService) {//: SwitchUserFilter() {
//    public override fun attemptSwitchUser(request: HttpServletRequest): Authentication? {
//        val current = SecurityContextHolder.getContext().authentication
//
//        val switchUserFilter = SwitchUserFilter()
//        switchUserFilter.setUserDetailsService(customUserDetailsService)
////        switchUserFilter.
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