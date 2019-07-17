package com.nicando.ediportal.common

import com.nicando.ediportal.common.apiResponse.EdiConnectionResponse
import com.nicando.ediportal.common.apiResponse.PagedResponse
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import com.nicando.ediportal.exceptions.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
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

    //TODO: securitycheck, only if user is admin || isparticipant
    fun findEdiConnection(id: Long): EdiConnectionResponse {
        return EdiConnectionResponse(ediConnectionRepository.findById(id).get())
    }

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

        validatePageNumberAndSize(pageNumber, pageSize)

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


    private fun validatePageNumberAndSize(pageNumber: Int, pageSize: Int) {
        if (pageNumber < 0) {
            logger.warn("Request with Page number = $pageNumber")
            throw BadRequestException("Page number cannot be less than zero.")
        }

        if (pageSize > MAX_PAGE_SIZE) {
            logger.warn("Request with Page pageSize = $pageSize , Max pageSize = $MAX_PAGE_SIZE")
            throw BadRequestException("Page pageSize must not be greater than $MAX_PAGE_SIZE")
        }
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)

        @Value("\$app.Constants.PageResponse.MAX_SIZE")
        private val MAX_PAGE_SIZE: Int = 50
    }
}