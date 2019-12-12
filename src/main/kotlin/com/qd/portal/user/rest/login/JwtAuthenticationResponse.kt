package com.qd.portal.user.rest.login

/**
 * Created by Jan Adamczyk on 26.06.2019.
 */
class JwtAuthenticationResponse(var accessToken: String?) {
    var tokenType = "Bearer"
}