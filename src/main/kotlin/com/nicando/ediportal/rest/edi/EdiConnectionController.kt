package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.EdiConnectionService
import com.nicando.ediportal.common.PagedResponse
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import com.nicando.ediportal.security.CurrentUser
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
class EdiConnectionController(private val ediConnectionRepository: EdiConnectionRepository, private val authenticationInfoService: AuthenticationInfoService,
                              private val ediConnectionService: EdiConnectionService) {
//    @PostMapping
//    fun createEdiConnection(): ResponseEntity<EdiConnection> {
//        var ediConnection:EdiConnection = null
//        var storedEdiConnection = ediConnectionRepository.save(ediConnection)
//        return ResponseEntity.ok(ediConnection)
//    }

    @GetMapping
    fun getEdiConnection(@RequestParam id: Long): ResponseEntity<EdiConnection> {
        return ediConnectionRepository.findById(id).map { ediConnection ->
            ResponseEntity.ok(ediConnection)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/all", produces = ["application/json"])
    fun getEdiConnections(@CurrentUser currentUser: UserPrincipal, request: HttpServletRequest,
                          @RequestParam pageNumber: Int, @RequestParam pageSize: Int): PagedResponse<EdiConnection> {
        logger.info("getEdiConnection Request by User: ${currentUser.username}")

        if (request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            return ediConnectionService.findEdiConnectionsForAdmin(pageNumber, pageSize)
        }

        return ediConnectionService.findEdiConnectionsForUser(pageNumber, pageSize)
    }

    @GetMapping("/customer")
    fun getEdiConnectionsForCustomer(@RequestParam id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(ediConnectionRepository.findEdiConnectionsByCustomerId(id))
    }

    @GetMapping("/supplier")
    fun getEdiConnectionsForSupplier(@RequestParam id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(ediConnectionRepository.findEdiConnectionsBySupplierId(id))
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}