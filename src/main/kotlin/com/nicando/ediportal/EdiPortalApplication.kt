package com.nicando.ediportal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EdiPortalApplication

fun main(args: Array<String>) {
    runApplication<EdiPortalApplication>(*args)
}
