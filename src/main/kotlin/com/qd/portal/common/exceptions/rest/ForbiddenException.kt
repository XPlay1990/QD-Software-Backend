package com.qd.portal.common.exceptions.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author : j_ada
 * @since : 12.08.2019, Mo.
 **/
@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException : RuntimeException {

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause)
}