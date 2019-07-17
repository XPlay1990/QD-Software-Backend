package com.nicando.ediportal.common.apiResponse

/**
 * Created by Jan Adamczyk on 16.07.2019.
 */
data class PagedResponse<T>(
        val content: List<T>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalElements: Long,
        val totalPages: Int,
        val isLastPage: Boolean
)