package com.nicando.ediportal.common.apiResponse.ediConnection

import com.nicando.ediportal.database.model.edi.EdiConnection

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */
data class EdiConnectionResponse(
        val content: EdiConnection
)