package com.nicando.ediportal.rest

import com.nicando.ediportal.database.model.organization.Organization
import com.nicando.ediportal.database.repositories.OrgRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@RestController
@RequestMapping("/org")
class OrgController(private val orgRepository: OrgRepository) {

    @PostMapping
    fun createOrg(): ResponseEntity<Organization> {
        val organization = Organization(0, "HH", 300000, "test@HH.eu",
                null, null, null)
        orgRepository.save(organization)
        return ResponseEntity.ok(organization)
    }

    @GetMapping
    fun getOrgById(@RequestParam id:Long): ResponseEntity<Organization> {
        return orgRepository.findById(id).map { org ->
            ResponseEntity.ok(org)
        }.orElse(ResponseEntity.notFound().build())
    }
}