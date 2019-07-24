package com.nicando.ediportal.rest

import com.nicando.ediportal.database.model.address.Address
import com.nicando.ediportal.database.model.address.Location
import com.nicando.ediportal.database.model.address.LocationType
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.message.PhoneMessage
import com.nicando.ediportal.database.model.edi.message.TextMessage
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.repositories.OrganizationRepository
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
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

        val address = Address("Komturstr.", 5, "Berlin", "12169", "Deutschland")
        val location = Location(address, LocationType.LOCATIONTYPE_PLANT, null)

        val createdEdiConnections = mutableListOf<EdiConnection>()
        repeat(30) {
            val random = Random()
            val customerName = Customer.values()[random.nextInt(Customer.values().size)].toString()
            val supplierName = Supplier.values()[random.nextInt(Supplier.values().size)].toString()
            val customer = Organization(customerName, 300, "test@testorg.de", null)
            val supplier = Organization(supplierName, 300, "test@testorg.de", null)
            customer.locations = mutableListOf(location)
            location.organization = customer
            organizationRepository.save(customer)
            organizationRepository.save(supplier)

            val textMessage = TextMessage(null, "testTitle", "testMsg")
            val phoneMessage = PhoneMessage(null, "testTitle", "testPhoneMsg")
//            val attachMentMessage = AttachmentMessage(null, "testTitle", "testPhoneMsg")

            val messages = mutableSetOf(textMessage, phoneMessage)
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

    enum class Customer {
        WOLF,
        KURTZ,
        ERCO,
        VAILLANT,
        HH,
        GARDENA,
        KEUCO,
        VAHLE
    }

    enum class Supplier {
        Supplier1,
        Supplier2,
        Supplier3,
        Supplier4,
        Supplier5
    }
}