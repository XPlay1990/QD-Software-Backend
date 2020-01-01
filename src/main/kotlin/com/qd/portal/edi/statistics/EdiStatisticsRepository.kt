package com.qd.portal.edi.statistics

import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.model.EdiStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 01.01.2020, Mi.
 **/

@Repository
interface EdiStatisticsRepository : JpaRepository<EdiConnection, Long> {
    fun countDistinctByStatus(status: EdiStatus) : Int
}