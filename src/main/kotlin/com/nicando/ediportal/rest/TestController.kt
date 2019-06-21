package com.nicando.ediportal.rest

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.message.Message
import com.nicando.ediportal.database.model.edi.message.PhoneMessage
import com.nicando.ediportal.database.model.edi.message.TextMessage
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import com.nicando.ediportal.database.repositories.OrganizationRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 21.06.2019.
 */
@RestController
@RequestMapping("/test")
class TestController(private val ediConnectionRepository: EdiConnectionRepository, private val organizationRepository: OrganizationRepository) {
    var LOGGER = LoggerFactory.getLogger(this.javaClass)

    @PostMapping
    fun createTest(): ResponseEntity<EdiConnection> {

        val organization = Organization(0, "testOrg", 300, "test@testorg.de", null, null, null)
        organizationRepository.save(organization)

        val textMessage = TextMessage(0, null, "test", "test", readByCustomer = true, readByNicando = true, readBySupplier = true)
        val phoneMessage = PhoneMessage(0, null, "test", "test", readByCustomer = true, readByNicando = true, readBySupplier = true)

        val list = mutableSetOf<Message>(textMessage, phoneMessage)
        val ediConnection = EdiConnection(0, "test", organization, organization, null, list)

        val storedConnection = ediConnectionRepository.save(ediConnection)

        return ResponseEntity.ok(storedConnection)
    }

    @GetMapping
    fun getTest():ResponseEntity<EdiConnection>{
        return ResponseEntity.ok(ediConnectionRepository.findAll()[0])
    }
}