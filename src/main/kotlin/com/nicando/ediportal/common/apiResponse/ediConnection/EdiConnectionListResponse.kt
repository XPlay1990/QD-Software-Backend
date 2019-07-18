package com.nicando.ediportal.common.apiResponse.ediConnection

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */
data class EdiConnectionListResponse<T>(
        val content: List<T>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalElements: Long,
        val totalPages: Int,
        val isLastPage: Boolean
)