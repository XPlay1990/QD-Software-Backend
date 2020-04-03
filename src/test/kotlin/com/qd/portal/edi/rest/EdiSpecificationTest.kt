package com.qd.portal.edi.rest

import com.qd.portal.common.rest.SearchCriteria
import com.qd.portal.edi.database.model.EdiConnection
import com.qd.portal.edi.database.repository.EdiConnectionRepository
import com.qd.portal.organization.database.repository.OrganizationRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional


/**
 * @author : j_ada
 * @since : 02.04.2020, Do.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
@Rollback
class EdiSpecificationTest {

    @Autowired
    lateinit var ediConnectionRepository: EdiConnectionRepository

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    private var ediConnectionOne: EdiConnection? = null
    private var ediConnectionTwo: EdiConnection? = null

    @BeforeEach
    fun init() {
        val organization1 = organizationRepository.findById(1).get()
        val organization2 = organizationRepository.findById(2).get()
        ediConnectionOne = ediConnectionRepository.save(EdiConnection(organization1, organization2))
        ediConnectionTwo = ediConnectionRepository.save(EdiConnection(organization2, organization1))
    }

    @Test
    fun customerIsOrg1() {
        val searchCriteriaList = ArrayList<SearchCriteria>()
        searchCriteriaList.add(SearchCriteria("customer.name", ":", organizationRepository.findById(1).get().name))
        val spec = EdiSpecification(searchCriteriaList)
        val results: List<EdiConnection> = ediConnectionRepository.findAll(spec)

        assertTrue(results.contains(ediConnectionOne))
    }
}