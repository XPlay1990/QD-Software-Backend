package com.nicando.ediportal.rest

import com.nicando.ediportal.common.organization.OrganizationService
import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.model.user.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/{id}/members")
    fun getAllMembers(@PathVariable id: Long): List<User>? {
        return organizationService.findAllOrganizationMembers(id)
    }

    @GetMapping("/customers")
    fun getAllCustomers(): List<Organization> {
        return organizationService.findAllCustomerOrgs()
    }

    @GetMapping("/suppliers")
    fun getAllSuppliers(): List<Organization> {
        return organizationService.findAllSupplierOrgs()
    }
}