package com.rikvanvelzen.codingtest.data.models.dto

import java.math.BigDecimal

data class CurrencyRatesDTO(

        val base: String? = null,
        val date: String? = null,
        val rates: Map<String, BigDecimal>? = null
)


