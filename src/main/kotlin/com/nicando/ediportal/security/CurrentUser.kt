package com.nicando.ediportal.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */


@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AuthenticationPrincipal
annotation class CurrentUser
