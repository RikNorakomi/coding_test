package com.rikvanvelzen.codingtest.data.models.domain

data class CurrencyRates (

        /**
         * Contains a collection of currency rate mapped as currency abbreviation to rate
         * compared to 1 unit of the base currency. F.e. rates is:
         * "AUD": 1.6169,
         * "BGN": 1.9564,
         * etc.
         */
        val rates: Map<String, Double>,
        val base: String
)