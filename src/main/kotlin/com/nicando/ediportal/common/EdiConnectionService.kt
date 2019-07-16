package com.nicando.ediportal.common

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class EdiConnectionService(private val ediConnectionRepository: EdiConnectionRepository, private val authenticationInfoService: AuthenticationInfoService) {

//    fun findEdiConnectionsForUser(): ResponseEntity<List<EdiConnection>> {
//        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
//        logger.info("Getting all Edi-Connections for Organization with Id: $organizationIdFromAuthentication")
//        val ediConnections = ediConnectionRepository
//                .findEdiConnectionsByCustomerIdOrSupplierId(organizationIdFromAuthentication, organizationIdFromAuthentication)
//        logger.info("Found following EdiConnections: $ediConnections")
//        return ResponseEntity.ok(ediConnections)
//    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findEdiConnectionsForAdmin(pageNumber: Int, pageSize: Int): PagedResponse<EdiConnection> {
        logger.info("Getting all Edi-Connections for Admin user.")

        val pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "updateTime")
        val ediConnectionsPage = ediConnectionRepository
                .findAll(pageable)

        return buildPagedResponse(ediConnectionsPage)
    }

    fun findEdiConnectionsForUser(pageNumber: Int, pageSize: Int): PagedResponse<EdiConnection> {
        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        logger.info("Getting all Edi-Connections for Organization with Id: $organizationIdFromAuthentication")

        val pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "updateTime")
        val ediConnectionsPage = ediConnectionRepository
                .findAllBySupplierIdOrCustomerId(organizationIdFromAuthentication, organizationIdFromAuthentication, pageable)

        return buildPagedResponse(ediConnectionsPage)
    }

    private fun buildPagedResponse(ediConnectionsPage: Page<EdiConnection>): PagedResponse<EdiConnection> {
        val ediConnections = ediConnectionsPage.content
        logger.info("Found ${ediConnections.size} matching EdiConnections")

        return PagedResponse(ediConnections, ediConnectionsPage.number,
                ediConnectionsPage.size, ediConnectionsPage.totalElements, ediConnectionsPage.totalPages, ediConnectionsPage.isLast)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}