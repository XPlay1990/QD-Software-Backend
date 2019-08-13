package com.nicando.ediportal.common.ediConnection

import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageListResponse
import com.nicando.ediportal.common.apiResponse.ediConnection.message.EdiMessageResponse
import com.nicando.ediportal.database.model.edi.EdiConnection
import com.nicando.ediportal.database.model.edi.EdiStatus
import com.nicando.ediportal.database.repositories.UserRepository
import com.nicando.ediportal.database.repositories.ediConnection.EdiConnectionRepository
import com.nicando.ediportal.database.repositories.organization.OrganizationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EdiConnectionService(private val ediConnectionRepository: EdiConnectionRepository,
                           private val organizationRepository: OrganizationRepository,
                           private val userRepository: UserRepository) {

    fun findEdiMessages(ediConnectionId: Long): EdiMessageListResponse {
        //TODO: Find better way to get Messages instead of pulling whole connection.
        //          BIDIRECTIONAL IS NOT!!! A BETTER SOLUTION! :>
        val messages = ediConnectionRepository.findById(ediConnectionId).get().messages
        val ediMessageResponses = mutableListOf<EdiMessageResponse>()
        messages?.forEach { message ->
            ediMessageResponses.add(EdiMessageResponse(message, message.javaClass.toString()))
        }
        return EdiMessageListResponse(ediMessageResponses)
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
    fun setDeveloperAndState(ediConnectionId: Long, assignedDeveloperId: Long, statusName: String) {
        logger.info("Saving Developer: $assignedDeveloperId and Status: $statusName for $ediConnectionId.")
        val ediConnection = ediConnectionRepository.findById(ediConnectionId).get()

        val developer = userRepository.findById(assignedDeveloperId).get()
        if (developer.organization.name != "Nicando") {
            throw IllegalStateException("Tried to set Developer from other Organization than Nicando")
        }
        ediConnection.assignedDeveloper = developer
        ediConnection.status = EdiStatus.valueOf(statusName)

        ediConnectionRepository.save(ediConnection)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}