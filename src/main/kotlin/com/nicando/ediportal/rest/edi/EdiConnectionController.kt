package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.common.ediConnection.EdiConnectionListService
import com.nicando.ediportal.common.apiResponse.ediConnection.EdiConnectionResponse
import com.nicando.ediportal.common.apiResponse.ediConnection.EdiConnectionListResponse
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.security.CurrentUser
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Copyright (C) 2019-2019 Jan Adamczyk <j_adamczyk@hotmail.com>
 *
 * Created: 22.06.19
 * This file is part of ediportal
 *
 * This Code can not be copied and/or distributed without the express
 * permission of Jan Adamczyk
 */
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CUSTOMER') || hasRole('ROLE_SUPPLIER')")
@RestController
@RequestMapping("/edi_connection")
class EdiConnectionController(private val ediConnectionListService: EdiConnectionListService) {
//    @PostMapping
//    fun createEdiConnection(): ResponseEntity<EdiConnection> {
//        var ediConnection:EdiConnection = null
//        var storedEdiConnection = ediConnectionRepository.save(ediConnection)
//        return ResponseEntity.ok(ediConnection)
//    }

    @GetMapping("/{id}")
    fun getEdiConnection(@PathVariable("id") id: Long): EdiConnectionResponse {
        return ediConnectionListService.findEdiConnection(id)
    }

    @GetMapping(produces = ["application/json"])
    fun getEdiConnections(@CurrentUser currentUser: UserPrincipal, request: HttpServletRequest,
                          @RequestParam pageNumber: Int, @RequestParam pageSize: Int): EdiConnectionListResponse<EdiConnection> {
        logger.info("getEdiConnection Request by User: ${currentUser.username}")

        if (request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            return ediConnectionListService.findEdiConnectionsForAdmin(pageNumber, pageSize)
        }

        return ediConnectionListService.findEdiConnectionsForUser(pageNumber, pageSize)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}