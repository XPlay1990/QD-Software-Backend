package com.qd.portal.edi.database.repository.messages

import com.qd.portal.edi.database.model.message.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EdiMessageRepository : JpaRepository<Message, Long> {

}