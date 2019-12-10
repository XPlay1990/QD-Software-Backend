package com.nicando.ediportal.organization

import com.nicando.ediportal.organization.service.OrganizationService
import com.nicando.ediportal.organization.database.model.Organization
import com.nicando.ediportal.user.database.model.User
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    fun getAllOrganizations(): List<Organization> {
        return organizationService.findAllOrgs()
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/members")
    fun getAllMembers(@PathVariable id: Long): List<User>? {
        return organizationService.findAllOrganizationMembers(id)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/nicando/members")
    fun getDevelopers(): List<User>? {
        return organizationService.findAllDevelopers()
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/customers")
    fun getAllCustomers(): List<Organization> {
        return organizationService.findAllCustomerOrgs()
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/suppliers")
    fun getAllSuppliers(): List<Organization> {
        return organizationService.findAllSupplierOrgs()
    }
}