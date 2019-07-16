package com.nicando.ediportal.common

import com.nicando.ediportal.exceptions.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

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
) {
    fun validatePageNumberAndSize(pageNumber: Int, pageSize: Int) {
        if (pageNumber < 0) {
            logger.warn("Request with Page number = $pageNumber")
            throw BadRequestException("Page number cannot be less than zero.")
        }

        if (pageSize > MAX_PAGE_SIZE) {
            logger.warn("Request with Page pageSize = $pageSize , Max pageSize = $MAX_PAGE_SIZE")
            throw BadRequestException("Page pageSize must not be greater than $MAX_PAGE_SIZE")
        }
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)

        @Value("\$app.Constants.PageResponse.MAX_SIZE")
        private val MAX_PAGE_SIZE: Int = 50


    }
}