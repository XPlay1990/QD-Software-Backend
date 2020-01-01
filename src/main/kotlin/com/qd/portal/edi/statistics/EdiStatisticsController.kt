package com.qd.portal.edi.statistics

import com.qd.portal.edi.database.model.EdiStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : j_ada
 * @since : 01.01.2020, Mi.
 **/
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGISTERED_USER')")
@RestController
@RequestMapping("/statistics")
class EdiStatisticsController(private val stateStatisticsService: EdiStatisticsService) {

    @GetMapping("/state")
    fun getStateStatistics(): MutableMap<EdiStatus, Int> {
        return stateStatisticsService.getStatusStatistics()
    }
}