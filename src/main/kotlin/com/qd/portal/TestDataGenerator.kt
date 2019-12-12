package com.qd.portal

import com.qd.portal.common.apiResponse.ResponseMessage
import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.model.message.Message
import com.qd.portal.edi.database.model.message.TextMessage
import com.qd.portal.organization.database.model.Organization
import com.qd.portal.user.database.model.RoleName
import com.qd.portal.user.database.model.Gender
import com.qd.portal.user.database.model.User
import com.qd.portal.user.database.repository.UserRepository
import com.qd.portal.edi.database.repository.EdiConnectionRepository
import com.qd.portal.organization.database.repository.OrganizationRepository
import com.qd.portal.user.service.roles.RoleService
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
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
class TestDataGenerator(private val ediConnectionRepository: EdiConnectionRepository,
                        private val organizationRepository: OrganizationRepository,
                        private val userRepository: UserRepository,
                        private val roleService: RoleService,
                        private val passwordEncoder: PasswordEncoder) {
    @PostMapping
    fun createTest(): ResponseMessage {
        val random = Random()

        for (index in 0..30) {
            val supplierName = "Supplier_$index"
            val supplier = Organization(supplierName, 0, "test@testorg.de", null)
            val savedSupplier = organizationRepository.save(supplier)
            val supplierUser = User("$supplierName-User", lorem.email, lorem.firstName, lorem.lastName, savedSupplier,
                    Gender.values()[random.nextInt(Gender.values().size)])
            supplierUser.password = passwordEncoder.encode("test")
            supplierUser.roles = mutableSetOf(roleService.findRoleByName(RoleName.ROLE_REGISTERED_USER))
            userRepository.save(supplierUser)
        }

        val customers = organizationRepository.findOrganizationsByNameNotLike("Supplier%")
        customers.forEach { customer ->
            val customerUser = User("${customer.name}-User", lorem.email,
                    lorem.firstName, lorem.lastName, customer,
                    Gender.values()[random.nextInt(Gender.values().size)])
            customerUser.roles = mutableSetOf(roleService.findRoleByName(RoleName.ROLE_REGISTERED_USER))
            customerUser.password = passwordEncoder.encode("test")
            userRepository.save(customerUser)
        }

        val suppliers = organizationRepository.findOrganizationsByNameLike("Supplier%")
        val users = userRepository.findAll()

        repeat(1000) {
            saveEdiConnection(customers, suppliers, users)
        }

        return ResponseMessage(true, "Success")
    }

    @Transactional
    @Async
    fun saveEdiConnection(customers: List<Organization>, suppliers: List<Organization>, users: List<User>) {
        logger.info("Creating Edi Connection")
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
                    dummyText.replace("PLACEHOLDER", lorem.getWords(random.nextInt(10) + 1)))

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

        ediConnectionRepository.save(ediConnection)
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