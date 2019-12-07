package com.nicando.ediportal.common

import com.nicando.ediportal.database.model.serverconfiguration.ServerConfiguration
import com.nicando.ediportal.database.repositories.serverConfiguration.ServerConfigurationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/
@Service
class ServerService(private val serverConfigurationRepository: ServerConfigurationRepository) {
    fun getServerConfiguration(): ServerConfiguration {
        try {
            return serverConfigurationRepository.findById(1).get()
        } catch (exception: Exception) {
            logger.error("ERROR GETTING SERVER CONFIGURATION!")
            throw exception
        }
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}