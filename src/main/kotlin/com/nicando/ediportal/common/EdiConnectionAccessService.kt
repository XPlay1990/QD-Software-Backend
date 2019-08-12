package com.nicando.ediportal.common

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.role.RoleName
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * @author : j_ada
 * @since : 12.08.2019, Mo.
 **/
@Service
class EdiConnectionAccessService(private val authenticationInfoService: AuthenticationInfoService) {

    fun hasUserAccessToEdiConnection(request: HttpServletRequest, ediConnection: EdiConnection): Boolean {
        val orgIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        if (!request.isUserInRole(RoleName.ROLE_ADMIN.toString())) {
            if (ediConnection.customer.id != orgIdFromAuthentication && ediConnection.supplier.id != orgIdFromAuthentication) {
                // User is not in Org with access to the connection
                return false
            }
        }
        return true
    }
}