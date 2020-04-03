package com.qd.portal.contact

import com.qd.portal.common.rest.apiResponse.ResponseMessage
import com.qd.portal.mail.EmailServiceImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


/**
 * Created by Jan Adamczyk on 20.06.2019.
 */
@RestController
@RequestMapping("/contact")
class ContactController(private val emailServiceImpl: EmailServiceImpl) {

    @PostMapping
    fun sendMessage(@Valid @RequestBody contactForm: ContactForm, request: HttpServletRequest): ResponseMessage {
        val params = mutableMapOf("fullName" to contactForm.fullName,
                "email" to contactForm.email, "message" to contactForm.message)
        emailServiceImpl.sendEmailWithTemplate("j_adamczyk@hotmail.com", "Contact Message",
                "mail/contact/contactMessage", params, request.locale)
        return ResponseMessage(true, "Successfully delivered message.")
    }
}

data class ContactForm(
        @field:NotEmpty
        val fullName: String,

        @field:Email
        @field:NotEmpty
        val email: String,
        @field:NotEmpty
        val message: String
)