package com.rikvanvelzen.codingtest.data.api

import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApi {

    @GET("/latest")
    fun getCurrencyRatesRx(@Query("base") base: String): Observable<CurrencyRatesDTO>
}