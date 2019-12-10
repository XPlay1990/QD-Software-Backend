package com.nicando.ediportal.user.rest

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


/**
 * Created by Jan Adamczyk on 21.05.2019.
 */


@RestController
@RequestMapping("/locale")
class LocaleController() {

    @GetMapping("/languages")
    fun getLanguages(): MutableList<LocalizedLanguageMapping> {
        val isoLanguages = Locale.getISOLanguages()
        val languageList = mutableListOf<LocalizedLanguageMapping>()
        isoLanguages.forEach { language ->
            val locale = Locale(language)
            languageList.add(LocalizedLanguageMapping(locale.language, locale.getDisplayLanguage(locale)))
        }
        return languageList
    }

    @GetMapping("/countries")
    fun getCountries(): MutableList<String> {
        val isoCountries = Locale.getISOCountries()
        val countryList = mutableListOf<String>()
        isoCountries.forEach { country ->
            val locale = Locale("", country)
            countryList.add(locale.getDisplayCountry(locale))
        }
        return countryList
    }

    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class LocalizedLanguageMapping(
        val isoLanguage: String,
        val localizedLanguage: String
)