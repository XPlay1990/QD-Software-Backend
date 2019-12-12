package com.qd.portal.server.database.repository

import com.qd.portal.server.database.model.ServerConfiguration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/

@Repository
interface ServerConfigurationRepository : JpaRepository<ServerConfiguration, Long>