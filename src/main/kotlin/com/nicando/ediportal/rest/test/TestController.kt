package com.nicando.ediportal.rest.test

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.edi.message.TextMessage
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.model.role.RoleName
import com.nicando.ediportal.database.model.user.User
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import com.nicando.ediportal.database.repositories.organization.OrganizationRepository
import com.nicando.ediportal.logic.roles.RoleService
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
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
class TestController(private val ediConnectionRepository: EdiConnectionRepository,
                     private val organizationRepository: OrganizationRepository,
                     private val userRepository: UserRepository,
                     private val roleService: RoleService,
                     private val passwordEncoder: PasswordEncoder) {
    @PostMapping
    @Transactional
    fun createTest(): ResponseEntity<MutableList<EdiConnection>> {
        val createdEdiConnections = mutableListOf<EdiConnection>()

        for (index in 0..30) {
            val supplierName = "Supplier_$index"
            val supplier = Organization(supplierName, 0, "test@testorg.de", null)
            val savedSupplier = organizationRepository.save(supplier)
            val supplierUser = User("$supplierName-User", lorem.email,
                    passwordEncoder.encode("test"), lorem.firstName, lorem.lastName, savedSupplier)
            supplierUser.roles = mutableListOf(roleService.findRoleByName(RoleName.ROLE_REGISTERED_USER))
            userRepository.save(supplierUser)
        }

        val customers = organizationRepository.findOrganizationsByNameNotLike("Supplier%")
        customers.forEach { customer ->
            val customerUser = User("${customer.name}-User", lorem.email,
                    customer.name, lorem.firstName, lorem.lastName, customer)
            customerUser.roles = mutableListOf(roleService.findRoleByName(RoleName.ROLE_REGISTERED_USER))
            userRepository.save(customerUser)
        }

        val suppliers = organizationRepository.findOrganizationsByNameLike("Supplier%")
        val users = userRepository.findAll()

        repeat(30) {
            val random = Random()
            val customer = customers[random.nextInt(customers.size)]
            val supplier = suppliers[random.nextInt(suppliers.size)]

            val messages = mutableListOf<Message>()

            repeat(random.nextInt(10)) {
                val textMessageUser = users[random.nextInt(users.size)]
                val dummyText = "{\n" +
                        "  \"blocks\": [\n" +
                        "    {\n" +
                        "      \"key\": \"13de6\",\n" +
                        "      \"text\": \"PLACEHOLDER\",\n" +
                        "      \"type\": \"unstyled\",\n" +
                        "      \"depth\": 0,\n" +
                        "      \"inlineStyleRanges\": [],\n" +
                        "      \"entityRanges\": [],\n" +
                        "      \"data\": {}\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"entityMap\": {}\n" +
                        "}"
                val textMessage = TextMessage(textMessageUser,
                        dummyText.replace("PLACEHOLDER", lorem.getWords(random.nextInt(10)+1)))

                // Attachments
//                val attachments: MutableSet<Attachment> = mutableSetOf()
//                val attachmentFile = ResourceUtils.getFile("classpath:EDI-Fragebogen.docx")

//                repeat(random.nextInt(2)) {
//                    val attachment = Attachment(lorem.name, ".docx",
//                            attachmentFile.length(), attachmentFile.readBytes())
//                    attachments.add(attachment)
//                }
//                val attachmentMessageUser = users[random.nextInt(users.size)]
//                val attachmentMessage = AttachmentMessage(attachmentMessageUser,
//                        lorem.getWords(random.nextInt(10)), attachments)
                messages.add(textMessage)
//                messages.add(attachmentMessage)
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
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}