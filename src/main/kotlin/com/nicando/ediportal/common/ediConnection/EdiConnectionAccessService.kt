package com.nicando.ediportal.common.ediConnection

import com.nicando.ediportal.common.AuthenticationInfoService
import com.nicando.ediportal.common.exceptions.rest.ForbiddenException
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.rest.edi.questions.AnswerController
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * @author : j_ada
 * @since : 12.08.2019, Mo.
 **/
@Service
class EdiConnectionAccessService(private val authenticationInfoService: AuthenticationInfoService) {

    fun hasUserAccessToEdiConnection(request: HttpServletRequest, ediConnection: EdiConnection, loggingString: String): Boolean {
        val orgIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        if (!request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            if (ediConnection.customer.id != orgIdFromAuthentication && ediConnection.supplier.id != orgIdFromAuthentication) {
                // User is not in Org with access to the connection
                logger.warn(loggingString)
                throw ForbiddenException("You are not allowed to access this Edi-Connection!")            }
        }
        return true
    }

    fun isUserSupplierOfEdiConnection(request: HttpServletRequest, ediConnection: EdiConnection, loggingString: String): Boolean {
        val orgIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        if (!request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            if (ediConnection.supplier.id != orgIdFromAuthentication) {
                // User is not in Org with access to the connection
                logger.warn(loggingString)
                throw ForbiddenException("You are not allowed to access this Edi-Connection!")            }
        }
        return true
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}