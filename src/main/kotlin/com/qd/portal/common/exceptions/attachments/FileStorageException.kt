package com.qd.portal.common.exceptions.attachments

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/
@ResponseStatus(HttpStatus.CONFLICT)
class FileStorageException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}