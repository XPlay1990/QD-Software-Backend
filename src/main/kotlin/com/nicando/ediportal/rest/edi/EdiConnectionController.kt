package com.nicando.ediportal.rest.edi

import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.repositories.EdiConnectionRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
class EdiConnectionController(private val ediConnectionRepository: EdiConnectionRepository) {

//    @PostMapping
//    fun createEdiConnection(): ResponseEntity<EdiConnection> {
//        var ediConnection:EdiConnection = null
//        var storedEdiConnection = ediConnectionRepository.save(ediConnection)
//        return ResponseEntity.ok(ediConnection)
//    }

    @GetMapping
    fun getEdiConnection(@RequestParam id: Long): ResponseEntity<EdiConnection> {
        return ediConnectionRepository.findById(id).map { ediConnection ->
            ResponseEntity.ok(ediConnection)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/all")
    fun getEdiConnections(): MutableList<EdiConnection> {
        return ediConnectionRepository.findAll()
    }

    @GetMapping("/customer")
    fun getEdiConnectionsForCustomer(@RequestParam id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(ediConnectionRepository.findEdiConnectionsByCustomerId(id))
    }

    @GetMapping("/supplier")
    fun getEdiConnectionsForSupplier(@RequestParam id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(ediConnectionRepository.findEdiConnectionsBySupplierId(id))
    }
}