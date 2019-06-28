package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import org.springframework.web.bind.annotation.*

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
@RequestMapping("/edi_connection/{ediConnectionId}")
class EdiMessageController(private val ediConnectionRepository: EdiConnectionRepository) {

    @PostMapping("/addMessage")
    fun addMessage(@PathVariable ediConnectionId: Long, @RequestBody test: String) {

    }
}