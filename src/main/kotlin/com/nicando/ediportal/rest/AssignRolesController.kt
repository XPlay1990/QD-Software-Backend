package com.nicando.ediportal.rest

import com.nicando.ediportal.database.repositories.RoleRepository
import com.nicando.ediportal.database.repositories.UserRepository
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Copyright (C) 2019-2019 Jan Adamczyk <j_adamczyk@hotmail.com>
 *
 * Created: 22.06.19
 * This file is part of ediportal
 *
 * This Code can not be copied and/or distributed without the express
 * permission of Jan Adamczyk
 */

@RestController
@RequestMapping("/edi_connection")
class AssignRolesController(private val userRepository: UserRepository, private val roleRepository: RoleRepository) {
    @PutMapping
    fun assignRolesToUser(@RequestParam userId: Long, @RequestParam roleId: Long) {
        //possibly just send json instead of single ids
    }
}