package com.nicando.ediportal.common.exceptions.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException : RuntimeException {

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause)
}