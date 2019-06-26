package com.nicando.ediportal.rest

import com.nicando.ediportal.database.model.address.Address
import com.nicando.ediportal.database.model.address.Location
import com.nicando.ediportal.database.model.address.LocationType
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

        val address = Address("Komturstr.", 5, "Berlin", "12169", "Deutschland")
        val location = Location(address, LocationType.LOCATIONTYPE_PLANT, null)
        val organization = Organization("testOrg", 300, "test@testorg.de", null, null, mutableListOf(location))
        location.organization = organization
        organizationRepository.save(organization)

        val textMessage = TextMessage(null, "test", "test")
        val phoneMessage = PhoneMessage(null, "test", "test")

        val list = mutableSetOf<Message>(textMessage, phoneMessage)
        val ediConnection = EdiConnection("test", organization, organization, null, list)

        val storedConnection = ediConnectionRepository.save(ediConnection)

        return ResponseEntity.ok(storedConnection)
    }

    @GetMapping
    fun getTest(): ResponseEntity<EdiConnection> {
        return ResponseEntity.ok(ediConnectionRepository.findAll()[0])
    }
}