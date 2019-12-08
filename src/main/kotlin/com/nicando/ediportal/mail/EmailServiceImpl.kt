package com.nicando.ediportal.mail

import com.nicando.ediportal.common.ServerService
import com.nicando.ediportal.database.model.serverconfiguration.Mode
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import java.util.*


/**
 * Created by Jan Adamczyk on 28.06.2019.
 */
@Service
class EmailServiceImpl(private val emailSender: JavaMailSender,
                       private val mailContentBuilder: MailContentBuilder,
                       private val serverService: ServerService) {
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

    fun sendEmailWithTemplate(to: String, subject: String, templateName: String, context: MutableMap<String, String>, locale: Locale) {
        logger.info("Sending Mail of type $templateName in language ${locale.language} to $to")

        addServerConfigurationToContext(context)
        val localizedTemplateName: String = when (locale.language) {
            "de" -> "${templateName}_${locale.language}"
            else -> "${templateName}_en"
        }

        val mimeMessage = emailSender.createMimeMessage()
        val message = MimeMessageHelper(mimeMessage, true, "UTF-8")
        setReceiverAndSubjectAccordingToServerMode(to, subject, message)
        message.setText(mailContentBuilder.build(localizedTemplateName, context), true)

        // HAS TO BE THE LAST LINE!
        message.addInline("footer", ClassPathResource("banner.jpg"), "image/jpg");

        emailSender.send(mimeMessage)
    }

    private fun addServerConfigurationToContext(context: MutableMap<String, String>) {
        val serverConfiguration = serverService.getServerConfiguration()
        context["ServerBackEndUrl"] = serverConfiguration.serverBackendUrl
        context["ServerFrontEndUrl"] = serverConfiguration.serverFrontEndUrl
        context["ServerFallBackEmail"] = serverConfiguration.fallBackEmail
        context["ServerSystemName"] = serverConfiguration.systemName
        context["ServerMode"] = serverConfiguration.mode.name
    }

    private fun setReceiverAndSubjectAccordingToServerMode(to: String, subject: String, message: MimeMessageHelper) {
        // Important, so that Test server do not send Mails to Customer if not wanted!
        val serverConfiguration = serverService.getServerConfiguration()
        val fallBackEmail = serverConfiguration.fallBackEmail
        val serverMode = serverConfiguration.mode
        val systemName = serverConfiguration.systemName

        when (serverMode) {
            Mode.TEST_MAILS_TO_FALLBACK -> {
                message.setFrom(serverConfiguration.fallBackEmail)
                message.setTo(fallBackEmail)
                message.setSubject("$systemName [${serverMode.name}] $subject")
            }
            Mode.TEST_MAILS_NORMAL -> {
                message.setFrom(serverConfiguration.fallBackEmail)
                message.setTo(to)
                message.setSubject("$systemName [${serverMode.name}] $subject")
            }
            Mode.TEST_MAILS_NONE -> {
                message.setFrom(serverConfiguration.fallBackEmail)
                message.setTo("none")
                message.setSubject("$systemName [${serverMode.name}] $subject")
            }
            Mode.PRODUCTION -> {
                message.setFrom("${serverConfiguration.systemName}@nicando.de")
                message.setTo(to)
                message.setSubject(subject)
            }
        }
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}