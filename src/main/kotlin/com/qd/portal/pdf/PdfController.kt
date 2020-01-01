package com.qd.portal.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@RestController
@RequestMapping("/pdf")
class PdfController(private val pdfGenerator: PdfGenerator) {

    @GetMapping
    fun getPdf(request: HttpServletRequest): ResponseEntity<PDDocument> {
        return pdfGenerator.generatePdf(request)
    }
}