/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/27/20 7:52 AM
 */

package com.rikvanvelzen.codingtest.data.providers

import android.annotation.SuppressLint

class CountryFlagUrlProvider {

    companion object {

        @SuppressLint("DefaultLocale")
        fun getCountryFlagIconUrl(currencyAbbreviation: String): String? {

            if (currencyAbbreviation.length < 2) return null

            var countryCode = currencyAbbreviation.substring(0, 2).toLowerCase()
            if (countryCode == "eu") {
                countryCode = "european_union"
            }

            return "https://hatscripts.github.io/circle-flags/flags/$countryCode.svg"
        }
    }
}