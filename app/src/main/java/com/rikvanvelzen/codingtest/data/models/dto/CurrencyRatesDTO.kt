package com.rikvanvelzen.codingtest.data.models.dto

data class CurrencyRatesDTO(

        val base: String? = null,
        val date: String? = null,
        val rates: Map<String, Double>? = null
)


