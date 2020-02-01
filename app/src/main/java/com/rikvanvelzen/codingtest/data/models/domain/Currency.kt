package com.rikvanvelzen.codingtest.data.models.domain

import java.math.BigDecimal

data class Currency(

        val abbreviation: String? = null,
        val name: String? = null,
        val countryCode: String? = null,
        val flagIconUrl: String? = null,

        val rate: BigDecimal? = null
)
