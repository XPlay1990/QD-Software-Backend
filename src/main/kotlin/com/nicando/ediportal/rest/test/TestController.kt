package com.nicando.ediportal.rest.test

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.message.Attachment
import com.nicando.ediportal.database.model.edi.message.AttachmentMessage
import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.edi.message.TextMessage
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.repositories.OrganizationRepository
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@RestController
@RequestMapping("/test")
class TestController(private val ediConnectionRepository: EdiConnectionRepository, private val organizationRepository: OrganizationRepository) {
    var LOGGER = LoggerFactory.getLogger(this.javaClass)

    @PostMapping
    fun createTest(): ResponseEntity<MutableList<EdiConnection>> {
        val createdEdiConnections = mutableListOf<EdiConnection>()

        for (index in 0..30) {
            val supplierName = "Supplier_$index"
            val supplier = Organization(supplierName, 0, "test@testorg.de", null)
            organizationRepository.save(supplier)
        }

        val customers = organizationRepository.findOrganizationsByNameNotLike("Supplier%")
        val suppliers = organizationRepository.findOrganizationsByNameLike("Supplier%")

        repeat(30) {
            val random = Random()
            val customer = customers[random.nextInt(customers.size)]
            val supplier = suppliers[random.nextInt(suppliers.size)]

            val messages = mutableSetOf<Message>()

            repeat(random.nextInt(10)) {
                val textMessage = TextMessage(null,
                        lorem.getTitle(random.nextInt(10)), lorem.getWords(random.nextInt(10)))

                // Attachments
                val attachments: MutableSet<Attachment> = mutableSetOf()
                val attachmentFile = ResourceUtils.getFile("classpath:EDI-Fragebogen.docx")
                repeat(random.nextInt(2)) {
                    val attachment = Attachment(lorem.name, ".docx",
                            attachmentFile.length(), attachmentFile.readBytes())
                    attachments.add(attachment)
                }
                val attachmentMessage = AttachmentMessage(null,
                        lorem.getTitle(random.nextInt(10)), lorem.getWords(random.nextInt(10)), attachments)
                messages.add(textMessage)
                messages.add(attachmentMessage)
            }
            val ediConnection = EdiConnection(customer, supplier)
            ediConnection.messages = messages

            createdEdiConnections.add(ediConnectionRepository.save(ediConnection))
        }

        return ResponseEntity.ok(createdEdiConnections)
    }

    @GetMapping
    fun getTest(): ResponseEntity<EdiConnection>? {
        val findAll = ediConnectionRepository.findAll()
        if (findAll.size > 0) {
            return ResponseEntity.ok(findAll[0])
        }
        return null
    }

    companion object {
        private val lorem: Lorem = LoremIpsum.getInstance()
    }
}