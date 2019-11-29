package com.nicando.ediportal.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context


/**
 * Created by Jan Adamczyk on 28.06.2019.
 */
@Service
class MailContentBuilder(private val templateEngine: TemplateEngine) {
    fun build(templateName: String, context: MutableMap<String, String>): String {
        val templateContext = Context()
        templateContext.setVariables(context as Map<String, Any>?)
        return templateEngine.process(templateName, templateContext)
    }
}