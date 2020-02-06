/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/6/20 9:59 PM
 */

package com.rikvanvelzen.codingtest.data.api

import io.reactivex.Observable
import retrofit2.http.GET

interface OpenExchangeRatesApi {

    @GET("/currencies.json")
    fun getCurrencyNamesRx(): Observable<Map<String, String>>

}