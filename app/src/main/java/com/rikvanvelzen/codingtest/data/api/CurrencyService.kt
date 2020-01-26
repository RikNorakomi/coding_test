package com.rikvanvelzen.codingtest.data.api

import com.rikvanvelzen.codingtest.data.models.dto.CurrencyNamesDTO
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("/latest")
    fun getCurrencyRates(@Query("base") base: String): Call<CurrencyRatesDTO>

    @GET("https://openexchangerates.org/api/currencies.json")
    fun getCurrencyNames(): Call<CurrencyNamesDTO>

    @GET("https://openexchangerates.org/api/currencies.json")
    fun getCurrencyNamesRx(): Observable<Map<String,String>>

    @GET("/latest")
    fun getCurrencyRatesRx(@Query("base") base: String): Observable<CurrencyRatesDTO>
}