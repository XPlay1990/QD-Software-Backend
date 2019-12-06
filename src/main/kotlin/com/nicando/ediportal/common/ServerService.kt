package com.nicando.ediportal.common

import com.nicando.ediportal.database.model.serverconfiguration.ServerConfiguration
import com.nicando.ediportal.database.repositories.serverConfiguration.ServerConfigurationRepository
import org.springframework.stereotype.Service


/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/
@Service
class ServerService(private val serverConfigurationRepository: ServerConfigurationRepository) {
    fun getServerConfiguration(): ServerConfiguration {
        return serverConfigurationRepository.findById(1).get()
    }
}