package com.nicando.ediportal.rest.edi.Messages

import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.nicando.ediportal.common.ediConnection.EdiConnectionService
import com.nicando.ediportal.database.model.edi.message.Message
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
@RequestMapping("/edi_connection/{ediConnectionId}/messages")
class EdiMessageController(private val ediConnectionService: EdiConnectionService) {

    @PostMapping
    fun addMessage(@PathVariable ediConnectionId: Long, @RequestBody test: String) {
        TODO("not implemented")
    }

    @GetMapping
    fun getMessages(@PathVariable ediConnectionId: Long): EdiMessageListResponse {
        return ediConnectionService.findEdiMessages(ediConnectionId)
    }
}