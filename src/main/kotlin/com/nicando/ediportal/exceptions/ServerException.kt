package com.nicando.ediportal.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ServerException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
