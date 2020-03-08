package com.qd.portal.real_estate.database.repository

import com.qd.portal.real_estate.database.model.Building
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : j_ada
 * @since : 15.01.2020, Mi.
 **/
@Repository
interface BuildingRepository : JpaRepository<Building, Long> {

}