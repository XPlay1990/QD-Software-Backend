package com.nicando.ediportal.rest.adminFunctions

import com.nicando.ediportal.common.admin.AdminFunctionsService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
class AdminFunctionsController(private val adminFunctionsService: AdminFunctionsService) {

//    @GetMapping("/switch/{userName}")
//    fun switchToUser(request: HttpServletRequest, @PathVariable userName: String) {
////        jwtTokenProvider.generateToken()
//        adminFunctionsService.attemptSwitchUser(request)
//    }
}