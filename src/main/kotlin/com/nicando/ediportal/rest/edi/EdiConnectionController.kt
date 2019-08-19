package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.EdiConnectionAccessService
import com.nicando.ediportal.common.apiResponse.ResponseMessage
import com.nicando.ediportal.common.apiResponse.ediConnection.EdiConnectionListResponse
import com.nicando.ediportal.common.apiResponse.ediConnection.EdiConnectionResponse
import com.nicando.ediportal.common.ediConnection.EdiConnectionListService
import com.nicando.ediportal.common.ediConnection.EdiConnectionService
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.EdiStatus
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.exceptions.ForbiddenException
import com.nicando.ediportal.security.CurrentUser
import com.nicando.ediportal.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGISTERED_USER')")
@RestController
@RequestMapping("/edi_connection")
class EdiConnectionController(private val ediConnectionListService: EdiConnectionListService,
                              private val ediConnectionService: EdiConnectionService,
                              private val authenticationInfoService: AuthenticationInfoService,
                              private val ediConnectionAccessService: EdiConnectionAccessService) {
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createEdiConnection(@RequestBody jsonInput: JsonInput): EdiConnection {
        return ediConnectionService.createEdiConnection(jsonInput.customerOrgId, jsonInput.custmerContactIdList,
                jsonInput.supplierOrgId, jsonInput.supplierContactIdList)
    }

    @GetMapping("/{id}")
    fun getEdiConnection(request: HttpServletRequest, @PathVariable("id") ediConnectionId: Long): EdiConnectionResponse {
        val foundEdiConnection = ediConnectionListService.findEdiConnection(ediConnectionId)

        if (!ediConnectionAccessService.hasUserAccessToEdiConnection(request, foundEdiConnection.content)) {
            logger.warn("User ${authenticationInfoService.getUsernameFromAuthentication()} " +
                    "tried to access Edi-Connection with ediConnectionId: $ediConnectionId which he is not allowed to!")
            throw ForbiddenException("You are not allowed to view this Edi-Connection!")
        }

        return foundEdiConnection
    }

    @GetMapping(produces = ["application/json"])
    fun getEdiConnections(@CurrentUser currentUser: UserPrincipal, request: HttpServletRequest,
                          @PageableDefault(size = 10, sort = ["updateTime"]) pageable: Pageable): EdiConnectionListResponse<EdiConnection> {
        logger.info("getEdiConnection Request by User: ${currentUser.username}")

        if (request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            return ediConnectionListService.findEdiConnectionsForAdmin(pageable)
        }

        return ediConnectionListService.findEdiConnectionsForUser(pageable)
    }


    @GetMapping("/possibleStates")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getEdiConnectionStateList(): Array<EdiStatus> {
        return EdiStatus.values()
    }

    @PostMapping("/saveDeveloperAndState")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun saveDeveloperAndState(@RequestBody saveDeveloperAndStateInput: SaveDeveloperAndStateInput): ResponseEntity<ResponseMessage> {
        ediConnectionService.setDeveloperAndState(saveDeveloperAndStateInput.ediConnectionId,
                saveDeveloperAndStateInput.developerId, saveDeveloperAndStateInput.state)
        return ResponseEntity(ResponseMessage(true, "Successfully saved Developer and State!"),
                HttpStatus.OK)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class JsonInput(
        val customerOrgId: Long,
        val custmerContactIdList: MutableList<Long>,
        val supplierOrgId: Long,
        val supplierContactIdList: MutableList<Long>
)

class SaveDeveloperAndStateInput(
        val ediConnectionId: Long,
        val developerId: Long?,
        val state: String
)