package com.nicando.ediportal.mail

import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File


/**
 * Created by Jan Adamczyk on 28.06.2019.
 */
@Service
class EmailServiceImpl(private val emailSender: JavaMailSender, private val mailContentBuilder: MailContentBuilder) {
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

    fun sendEmailWithTemplate(to: String, subject: String, name: String, text: String) {
        logger.info("Sending Mail to $to")
        val mimeMessage = emailSender.createMimeMessage()
        val message = MimeMessageHelper(mimeMessage, true, "UTF-8")
        message.setTo(to)
        message.setSubject(subject)
        message.setText(mailContentBuilder.build(name, text), true)
        message.addInline("footer", ClassPathResource("banner.jpg"), "image/jpg");
        emailSender.send(mimeMessage)
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}