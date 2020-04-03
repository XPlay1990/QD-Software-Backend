package com.qd.portal.edi.rest

import com.qd.portal.common.rest.SearchCriteria
import com.qd.portal.edi.database.model.EdiConnection
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.*

/**
 * @author : j_ada
 * @since : 02.04.2020, Do.
 **/
class EdiSpecification(
        private val searchCriteriaList: List<SearchCriteria>
) : Specification<EdiConnection> {
    override fun toPredicate(root: Root<EdiConnection>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate? {

        if (searchCriteriaList.isEmpty()) {
            return null
        } else {
            val predicateList = ArrayList<Predicate>()
            searchCriteriaList.forEach { searchCriteria ->
                val searchKeys = searchCriteria.key.split(".")
                var path = root.get<Any>(searchKeys[0])
                searchKeys.forEachIndexed { index, searchKey ->
                    if (index != 0) {
                        path = path.get(searchKey)
                    }
                }
                when (searchCriteria.operation) {
                    ">" -> {
                        predicateList.add(builder.greaterThanOrEqualTo(path as Path<String>, searchCriteria.value.toString()))
                    }
                    "<" -> {
                        predicateList.add(builder.lessThanOrEqualTo(path as Path<String>, searchCriteria.value.toString()))
                    }
                    ":" -> {
                        if (path.javaType === String::class.java) {
                            predicateList.add(builder.like(path as Path<String>, "%" + searchCriteria.value + "%"))
                        } else {
                            predicateList.add(builder.equal(path, searchCriteria.value))
                        }
                    }
                }
            }
            var result = predicateList[0]
            predicateList.forEachIndexed { index, predicate ->
                if (index > 0) {
                    result = builder.and(result, predicate)
                }
            }
            return result
        }
    }
}