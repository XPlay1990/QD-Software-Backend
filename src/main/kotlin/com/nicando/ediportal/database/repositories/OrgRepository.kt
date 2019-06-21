package com.nicando.ediportal.database.repositories

import com.nicando.ediportal.database.model.organization.Organization
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */
interface OrgRepository : JpaRepository<Organization, Long>