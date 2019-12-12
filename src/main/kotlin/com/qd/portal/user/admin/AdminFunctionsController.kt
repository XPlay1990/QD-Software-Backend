package com.qd.portal.user.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
class AdminFunctionsController() {

//    @GetMapping("/switch/{userName}")
//    fun switchToUser(request: HttpServletRequest, @PathVariable userName: String) {
////        jwtTokenProvider.generateToken()
////        adminFunctionsService.attemptSwitchUser(request)
//    }
}