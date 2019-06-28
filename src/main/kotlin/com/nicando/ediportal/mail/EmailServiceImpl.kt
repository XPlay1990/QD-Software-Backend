package com.nicando.ediportal.mail

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File
import javax.mail.internet.MimeMessage


/**
 * Created by Jan Adamczyk on 28.06.2019.
 */
@Component
class EmailServiceImpl(private val emailSender: JavaMailSender) {
    fun sendSimpleMessage(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }

    fun sendMessageWithAttachment(
            to: String, subject: String, text: String, pathToAttachment: String) {

        val message = emailSender.createMimeMessage()

        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)

        val file = FileSystemResource(File(pathToAttachment))
        helper.addAttachment("Invoice", file)

        emailSender.send(message)
    }
}