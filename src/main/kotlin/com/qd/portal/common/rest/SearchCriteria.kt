package com.qd.portal.common.rest

data class SearchCriteria(
        val key: String,
        val operation: String,
        val value: Any
)