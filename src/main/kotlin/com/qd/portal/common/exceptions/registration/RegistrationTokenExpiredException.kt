package com.qd.portal.common.exceptions.registration

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author : j_ada
 * @since : 07.12.2019, Sa.
 **/

@ResponseStatus(HttpStatus.BAD_REQUEST)
class RegistrationTokenExpiredException : RuntimeException {
    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause)
}
