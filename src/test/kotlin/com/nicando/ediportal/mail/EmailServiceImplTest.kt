package com.nicando.ediportal.mail

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author : j_ada
 * @since : 16.10.2019, Mi.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class EmailServiceImplTest() {

    @Autowired
    lateinit var emailServiceImpl: EmailServiceImpl

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun sendSimpleMessage() {
        emailServiceImpl.sendSimpleMessage("j_adamczyk@hotmail.com", "test", "This is a test Mail!")
    }

    @Test
    fun sendMessageWithAttachment() {
    }


    @Test
    fun sendEmailWithTemplate() {
        emailServiceImpl.sendEmailWithTemplate("j_adamczyk@hotmail.com", "test", "Jan Adamczyk", "This is a test Mail!")
    }
}
