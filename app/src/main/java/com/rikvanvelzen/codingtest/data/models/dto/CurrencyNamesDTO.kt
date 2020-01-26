package com.rikvanvelzen.codingtest.data.models.dto

data class CurrencyNamesDTO(

        /**
         * Maps currency abbreviation to full name. So f.e.:
         * "AED": "United Arab Emirates Dirham",
         * "AFN": "Afghan Afghani",
         *  etc.
         */
        val names: Map<String, String>? = null

)


