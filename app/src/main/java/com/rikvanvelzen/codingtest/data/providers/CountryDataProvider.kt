/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/27/20 7:52 AM
 */

package com.rikvanvelzen.codingtest.data.providers

import android.annotation.SuppressLint
import java.util.*

class CountryDataProvider {

    val COUNTRY_FLAGS_URL = "https://hatscripts.github.io/circle-flags/flags/"
    val IMAGE_FILE_TYPE = ".svg"
    val countryCodeEU = "european_union"

    @SuppressLint("DefaultLocale")
    fun getCountryFlagUrl(currencyAbbreviation: String): String? {

        if (currencyAbbreviation.length < 2) return null

        var countryCode = currencyAbbreviation.substring(0, 2).toLowerCase()
        if (countryCode == "eu") {
            countryCode = countryCodeEU
        }

        return "$COUNTRY_FLAGS_URL$countryCode$IMAGE_FILE_TYPE"
    }

    fun getCountryCodeFromName(countryName: String?): String? {

        val countries: MutableMap<String, String> = HashMap()

        for (iso in Locale.getISOCountries()) {
            val locale = Locale("", iso)
            countries[locale.displayCountry] = iso
        }

        return countries[countryName]
    }
}