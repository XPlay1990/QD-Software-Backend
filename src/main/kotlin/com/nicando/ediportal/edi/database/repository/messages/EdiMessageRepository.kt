package com.nicando.ediportal.edi.database.repository.messages

import com.nicando.ediportal.edi.database.model.message.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EdiMessageRepository : JpaRepository<Message, Long> {

}