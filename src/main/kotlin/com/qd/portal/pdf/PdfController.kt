package com.qd.portal.pdf

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import javax.servlet.http.HttpServletResponse


/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@RestController
@RequestMapping("/pdf")
class PdfController(private val pdfGenerator: PdfGenerator) {

    @GetMapping
    fun getPdf(response: HttpServletResponse) {
        response.addHeader("Content-disposition", "attachment;filename=\"MyPdf.pdf\"");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.contentType = MediaType.APPLICATION_PDF.toString()
        pdfGenerator.generatePdf()
                .use { pdfDocument ->
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    pdfDocument.save(byteArrayOutputStream)
                    pdfDocument.close()
                    response.outputStream.write(byteArrayOutputStream.toByteArray())
                }
    }
}