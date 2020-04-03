package com.qd.portal.common.rest

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author : j_ada
 * @since : 03.04.2020, Fr.
 **/
class SearchCriteriaBuilder {
    fun build(searchString: String?): List<SearchCriteria> {
        val searchCriteriaList = ArrayList<SearchCriteria>()
        if(searchString != null) {
            val pattern: Pattern = Pattern.compile("([\\w.]+)(:|<|>)([\\w. ]+),")
            val matcher: Matcher = pattern.matcher("$searchString,")

            while (matcher.find()) {
                searchCriteriaList.add(SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)))
            }
        }
        return searchCriteriaList
    }
}