package com.nicando.ediportal.server.database.model

/**
 * @author : j_ada
 * @since : 06.12.2019, Fr.
 **/

import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by Jan Adamczyk on 22.05.2019.
 */
@Entity
data class ServerConfiguration(
        @NotBlank
        val serverFrontEndUrl: String,

        @NotBlank
        val serverBackendUrl: String,

        val systemName: String,

        @Enumerated(EnumType.STRING)
        val mode: Mode,

        val fallBackEmail: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

enum class Mode {
    TEST_MAILS_TO_FALLBACK,
    TEST_MAILS_NORMAL,
    TEST_MAILS_NONE,
    PRODUCTION
}