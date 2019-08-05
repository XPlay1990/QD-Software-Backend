package com.nicando.ediportal.rest.edi.messages

/**
 * @author : j_ada
 * @since : 02.08.2019, Fr.
 **/

class FileStorageException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}