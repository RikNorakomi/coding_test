package com.rikvanvelzen.codingtest.data.models.domain

data class Currency(

        val abbreviation: String? = null,
        val name: String? = null,
        val countryCode: String? = null,

        val rate: Float? = null,
        val iconUrl: String? = null
)
