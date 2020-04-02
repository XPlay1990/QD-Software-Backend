package com.qd.portal.edi.rest

import com.qd.portal.edi.database.model.EdiConnection
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.*

/**
 * @author : j_ada
 * @since : 02.04.2020, Do.
 **/
class EdiSpecification(
        private val criteria: SearchCriteria
) : Specification<EdiConnection> {
    override fun toPredicate(root: Root<EdiConnection>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate? {
        val searchKeys = criteria.key.split(".")
        var path = root.get<Any>(searchKeys[0])
        searchKeys.forEachIndexed { index, searchKey ->
            if (index != 0) {
                path = path.get(searchKey)
            }
        }
        when (criteria.operation) {
            ">" -> {
                return builder.greaterThanOrEqualTo(path as Path<String>, criteria.value.toString())
            }
            "<" -> {
                return builder.lessThanOrEqualTo(path as Path<String>, criteria.value.toString())
            }
            ":" -> {
                return if (path.javaType === String::class.java) {
                    builder.like(path as Path<String>, "%" + criteria.value + "%")
                } else {
                    builder.equal(path, criteria.value);
                }
            }
            else -> return null
        }
    }
}

data class SearchCriteria(
        val key: String,
        val operation: String,
        val value: Any
)