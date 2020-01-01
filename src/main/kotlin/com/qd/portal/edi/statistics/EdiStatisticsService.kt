package com.qd.portal.edi.statistics

import com.qd.portal.edi.database.model.EdiStatus
import org.springframework.stereotype.Service

/**
 * @author : j_ada
 * @since : 01.01.2020, Mi.
 **/
@Service
class EdiStatisticsService(private val statisticsRepository: EdiStatisticsRepository) {
    fun getStatusStatistics(): MutableMap<EdiStatus, Int> {
        val statistics = mutableMapOf<EdiStatus, Int>()
        EdiStatus.values().forEach { status -> statistics[status] = statisticsRepository.countDistinctByStatus(status) }
        return statistics
    }
}