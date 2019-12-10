package com.nicando.ediportal.edi.service

import com.nicando.ediportal.user.service.AuthenticationInfoService
import com.nicando.ediportal.edi.database.model.EdiConnection
import com.nicando.ediportal.edi.database.model.EdiStatus
import com.nicando.ediportal.user.database.repository.UserRepository
import com.nicando.ediportal.edi.database.repository.EdiConnectionRepository
import com.nicando.ediportal.organization.database.repository.OrganizationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EdiConnectionService(private val ediConnectionRepository: EdiConnectionRepository,
                           private val organizationRepository: OrganizationRepository,
                           private val userRepository: UserRepository,
                           private val authenticationInfoService: AuthenticationInfoService) {

    @Transactional
    fun findEdiConnection(id: Long): EdiConnection {
        val ediConnection = ediConnectionRepository.findById(id).get()

        val organizationIdFromAuthentication = authenticationInfoService.getOrgIdFromAuthentication()
        setReadByOrg(ediConnection, organizationIdFromAuthentication)

        return ediConnection
    }

    @Transactional
    fun createEdiConnection(customerOrgId: Long, customerContactIdList: MutableList<Long>,
                            supplierOrgId: Long, supplierContactIdList: MutableList<Long>): EdiConnection {
        logger.info("Starting to create Ediconnection between Customer: $customerOrgId and Supplier: $supplierOrgId")

        val customerOrg = organizationRepository.findById(customerOrgId).get()
        val supplierOrg = organizationRepository.findById(supplierOrgId).get()
        val customerContactList = userRepository.findAllById(customerContactIdList)
        val supplierContactList = userRepository.findAllById(supplierContactIdList)

        val newEdiConnection = EdiConnection(customerOrg, supplierOrg)
        newEdiConnection.customerContacts = customerContactList
        newEdiConnection.supplierContacts = supplierContactList

        return ediConnectionRepository.save(newEdiConnection)
    }

    @Transactional
    fun setDeveloperAndState(ediConnectionId: Long, assignedDeveloperId: Long?, statusName: String) {
        logger.info("Saving Developer: $assignedDeveloperId and Status: $statusName for $ediConnectionId.")
        val ediConnection = ediConnectionRepository.findById(ediConnectionId).get()

        val developer = assignedDeveloperId?.let { userRepository.findById(it).get() }
        if (developer != null) {
            if (developer.organization.name != "Nicando") {
                throw IllegalStateException("Tried to set Developer from other Organization than Nicando")
            }
        }
        ediConnection.assignedDeveloper = developer
        ediConnection.status = EdiStatus.valueOf(statusName)

        ediConnectionRepository.save(ediConnection)
    }

    private fun setReadByOrg(ediConnection: EdiConnection, organizationIdFromAuthentication: Long) {
        when {
            authenticationInfoService.getOrgNameFromAuthentication() == "Nicando" -> {
                ediConnection.readByNicando = true
            }
            organizationIdFromAuthentication == ediConnection.customer.id -> {
                ediConnection.readByCustomer = true
            }
            organizationIdFromAuthentication == ediConnection.supplier.id -> {
                ediConnection.readBySupplier = true
            }
        }
        ediConnectionRepository.save(ediConnection)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}