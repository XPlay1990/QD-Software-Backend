package com.nicando.ediportal.rest

import com.nicando.ediportal.common.organization.OrganizationService
import com.nicando.ediportal.database.model.organization.Organization
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@RestController
@RequestMapping("/org")
class OrganizationController(private val organizationService: OrganizationService) {

//    @PostMapping
//    fun createOrganization(): ResponseEntity<Organization> {
//        val organization = Organization(0, "HH", 300000, "test@HH.eu",
//                null, null, null)
//        val storedOrganization = organizationRepository.save(organization)
//        return ResponseEntity.ok(storedOrganization)
//    }

    @GetMapping
    fun getOrganizationById(@RequestParam id: Long): ResponseEntity<Organization> {
        return organizationService.findOrganizationById(id).map { organization ->
            ResponseEntity.ok(organization)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/all")
    fun getAllOrganizations() {
        //TODO: IMPLEMENT
    }

    @GetMapping("/customers")
    fun getAllCustomers(): List<Organization> {
        return organizationService.findAllCustomerOrgs()
    }
}