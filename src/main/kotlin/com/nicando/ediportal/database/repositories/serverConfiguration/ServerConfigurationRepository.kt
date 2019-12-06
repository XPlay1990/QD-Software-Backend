package com.nicando.ediportal.database.repositories.serverConfiguration

import com.nicando.ediportal.database.model.serverconfiguration.ServerConfiguration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/

@Repository
interface ServerConfigurationRepository : JpaRepository<ServerConfiguration, Long>