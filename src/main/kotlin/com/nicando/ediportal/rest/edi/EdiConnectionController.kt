package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import com.nicando.ediportal.security.service.AuthenticationInfoService
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
class EdiConnectionController(private val ediConnectionRepository: EdiConnectionRepository, private val authenticationInfoService: AuthenticationInfoService) {
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
    fun getEdiConnections(request: HttpServletRequest): ResponseEntity<List<EdiConnection>> {
        if (request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            logger.info("Admin requested all EDI-Connections")
            return ResponseEntity.ok(ediConnectionRepository.findAll())
        }

        return findEdiConnectionsForUser()
    }

    //TODO: move
    private fun findEdiConnectionsForUser(): ResponseEntity<List<EdiConnection>> {
        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        logger.info("Getting all Edi-Connections for Organization with Id: $organizationIdFromAuthentication")
        val ediConnections = ediConnectionRepository
                .findEdiConnectionsByCustomerIdOrSupplierId(organizationIdFromAuthentication, organizationIdFromAuthentication)
        logger.info("Found following EdiConnections: $ediConnections")
        return ResponseEntity.ok(ediConnections)
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