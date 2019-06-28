package com.nicando.ediportal.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context


/**
 * Created by Jan Adamczyk on 28.06.2019.
 */
@Service
class MailContentBuilder(private val templateEngine: TemplateEngine) {

    fun build(message: String): String {
        val context = Context()
        context.setVariable("message", message)
        return templateEngine.process("mail.testMail", context)
    }

}