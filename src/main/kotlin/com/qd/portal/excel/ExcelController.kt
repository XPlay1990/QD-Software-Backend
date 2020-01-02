package com.qd.portal.excel

import com.qd.portal.user.database.model.RoleName
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author : j_ada
 * @since : 02.01.2020, Do.
 **/
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGISTERED_USER')")
@RestController
@RequestMapping("/edi_connections/excel")
class ExcelController(private val excelService: ExcelService) {

    @GetMapping
    fun getEdiConnectionsExcelFile(request: HttpServletRequest, response: HttpServletResponse) {
        response.addHeader("Content-disposition", "attachment;filename=EdiConnections.xslx");
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        excelService.createEdiConnectionsExcelRepresentation(request.isUserInRole(RoleName.ROLE_ADMIN.toString()))
                .use { xssfWorkbook ->
                    xssfWorkbook.write(response.outputStream as OutputStream)
                }
    }
}