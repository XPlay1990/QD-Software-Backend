package com.qd.portal.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service


/**
 * @author : j_ada
 * @since : 27.12.2019, Fr.
 **/

@Service
class PdfGenerator {
    fun generatePdf(): PDDocument {
        val document = PDDocument()
        val page = PDPage()
        document.addPage(page)

        val contentStream = PDPageContentStream(document, page)

        contentStream.setFont(PDType1Font.COURIER, 12f)
        contentStream.beginText()
        contentStream.showText("Hello World")
        contentStream.endText()


        val image = PDImageXObject.createFromByteArray(document,
                ClassPathResource("banner.png").inputStream.readAllBytes(), "banner")
        contentStream.drawImage(image, 0f, 0f)
        contentStream.close()

//        document.save("pdfBoxHelloWorld.pdf")
//        document.close()

        return document
    }

//    private fun generateResponse(document: PDDocument): ResponseEntity<PDDocument> {
//        var contentType = MediaType.APPLICATION_PDF.toString()
//        // Fallback to the default content type if type could not be determined
//        val contentDisposition = ContentDisposition.builder("inline")
//                .filename("yourpdf.pdf")
//                .build()
//
//        val headers = HttpHeaders()
//        headers.contentDisposition = contentDisposition
////        headers.accessControlExposeHeaders = mutableListOf(HttpHeaders.CONTENT_DISPOSITION)
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType(contentType))
//                .body(document)
//    }
}