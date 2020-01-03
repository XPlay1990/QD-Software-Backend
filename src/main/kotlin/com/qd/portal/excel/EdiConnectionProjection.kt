package com.qd.portal.excel

import com.qd.portal.edi.database.model.EdiStatus
import com.qd.portal.user.database.model.User
import java.time.LocalDateTime

/**
 * @author : j_ada
 * @since : 03.01.2020, Fr.
 **/
interface EdiConnectionProjection {
    val customer: String
    val supplier: String
    val status: EdiStatus
    val dev: User?
    val updateTime: LocalDateTime?
}