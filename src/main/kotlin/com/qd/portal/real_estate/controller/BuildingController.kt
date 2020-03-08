package com.qd.portal.real_estate.controller

import com.qd.portal.real_estate.database.model.Building
import com.qd.portal.real_estate.service.BuildingService
import com.qd.portal.user.service.AuthenticationInfoService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : j_ada
 * @since : 15.01.2020, Mi.
 **/
@RestController
@RequestMapping("/building")
class BuildingController(private val authenticationInfoService: AuthenticationInfoService,
                         private val buildingService: BuildingService) {
    @PostMapping
    fun addBuilding(@RequestBody building: Building) {
        logger.info("Adding building for user ${authenticationInfoService.getUsernameFromAuthentication()}")

        buildingService.addBuilding(building, authenticationInfoService.getOrgIdFromAuthentication())
    }

    fun deleteBuilding() {

    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}