package com.qd.portal.mail

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

/**
 * @author : j_ada
 * @since : 16.10.2019, Mi.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    lateinit var emailServiceImpl: EmailServiceImpl

    @BeforeAll
    fun setUp() {
    }

    @AfterAll
    fun tearDown() {
    }

    @Test
    fun sendSimpleMessage() {
//        emailServiceImpl.sendSimpleMessage("j_adamczyk@hotmail.com", "test", "This is a test Mail!")
    }

    @Test
    fun sendMessageWithAttachment() {
    }


    @Test
    fun sendEmailWithTemplate() {
        emailServiceImpl.sendEmailWithTemplate("j_adamczyk@hotmail.com", "test",
                "mail/registration/registrationToken",
                mutableMapOf("FirstName" to "user.firstName", "LastName" to "user.lastName", "UserName" to "user.username",
                        "Gender" to "user.gender.name", "VerificationToken" to "verificationToken.token", "ExpirationHours" to 48.toString()),
                Locale.ENGLISH)
    }
}
