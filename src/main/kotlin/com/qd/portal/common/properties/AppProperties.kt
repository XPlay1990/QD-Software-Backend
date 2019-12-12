package com.qd.portal.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author : j_ada
 * @since : 03.12.2019, Di.
 **/
@Component
@ConfigurationProperties(prefix = "app")
data class AppProperties(val constants: Constants = Constants()) {
    class Constants {
        lateinit var jwtSecret: String
        lateinit var jwtExpirationInMs: String
        lateinit var pageResponseMaxSize: String
        lateinit var uploadDirectory: String
    }
}