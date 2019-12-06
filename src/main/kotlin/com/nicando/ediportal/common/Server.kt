package com.nicando.ediportal.common

import com.nicando.ediportal.database.model.serverconfiguration.ServerConfiguration
import com.nicando.ediportal.database.repositories.serverConfiguration.ServerConfigurationRepository
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope


/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/
@Configuration
class Server(private val serverConfigurationRepository: ServerConfigurationRepository) {
    @Bean("accountService")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun getServerConfiguration(): ServerConfiguration {
        return serverConfigurationRepository.findById(1).get()
    }
}