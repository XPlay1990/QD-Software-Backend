package com.nicando.ediportal.database.repositories.ediConnection.messages

import com.nicando.ediportal.database.model.edi.message.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EdiMessageRepository : JpaRepository<Message, Long> {

}