package com.qd.portal.edi.service

import com.qd.portal.common.apiResponse.ediConnection.EdiConnectionListResponse
import com.qd.portal.common.exceptions.rest.BadRequestException
import com.qd.portal.common.properties.AppProperties
import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.repository.EdiConnectionRepository
import com.qd.portal.user.service.AuthenticationInfoService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service


/**
 * Created by Jan Adamczyk on 15.07.2019.
 */
@Service
class EdiConnectionListService(private val ediConnectionRepository: EdiConnectionRepository,
                               private val authenticationInfoService: AuthenticationInfoService,
                               appProperties: AppProperties) {

    private val MAX_PAGE_SIZE: Int = Integer.valueOf(appProperties.constants.pageResponseMaxSize)

    fun findEdiConnectionsPage(pageable: Pageable, isAdmin: Boolean): EdiConnectionListResponse<EdiConnection> {
        return if (isAdmin) {
            findEdiConnectionsPageForAdmin(pageable)
        } else {
            findEdiConnectionsPageForUser(pageable)
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun findEdiConnectionsPageForAdmin(pageable: Pageable)
            : EdiConnectionListResponse<EdiConnection> {
        logger.info("Getting Edi-Connections page for Admin user.")

        val ediConnectionsPage = ediConnectionRepository
                .findAll(pageable)

        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        isReadByOrg(ediConnectionsPage, organizationIdFromAuthentication)

        return buildPagedResponse(ediConnectionsPage)
    }

    fun findEdiConnectionsPageForUser(pageable: Pageable)
            : EdiConnectionListResponse<EdiConnection> {
        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        logger.info("Getting Edi-Connections page for Organization with Id: $organizationIdFromAuthentication")

        validatePageNumberAndSize(pageable.pageNumber, pageable.pageSize)

        val ediConnectionsPage = ediConnectionRepository
                .findAllBySupplierIdOrCustomerId(organizationIdFromAuthentication, organizationIdFromAuthentication, pageable)

        isReadByOrg(ediConnectionsPage, organizationIdFromAuthentication)

        return buildPagedResponse(ediConnectionsPage)
    }

    fun findAllEdiConnections(isAdmin: Boolean): MutableList<EdiConnection> {
        return if (isAdmin) {
            ediConnectionRepository.findAll()
        } else {
            val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
            ediConnectionRepository.findAllBySupplierIdOrCustomerId(organizationIdFromAuthentication, organizationIdFromAuthentication)
        }
    }

    private fun isReadByOrg(ediConnectionsPage: Page<EdiConnection>, organizationIdFromAuthentication: Long) {
        for (ediConnection in ediConnectionsPage.content) {
            when {
                authenticationInfoService.getOrgNameFromAuthentication() == "QD Software" -> {
                    ediConnection.read = ediConnection.readByQDSoftware
                }
                organizationIdFromAuthentication == ediConnection.customer.id -> {
                    ediConnection.read = ediConnection.readByCustomer
                }
                organizationIdFromAuthentication == ediConnection.supplier.id -> {
                    ediConnection.read = ediConnection.readBySupplier
                }
            }
        }
    }

    private fun buildPagedResponse(ediConnectionsPage: Page<EdiConnection>): EdiConnectionListResponse<EdiConnection> {
        val ediConnections = ediConnectionsPage.content
        logger.info("Found ${ediConnections.size} matching EdiConnections")

        return EdiConnectionListResponse(ediConnections, ediConnectionsPage.number,
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
    }
}