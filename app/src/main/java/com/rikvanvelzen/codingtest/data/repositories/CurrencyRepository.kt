/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.data.repositories

import com.rikvanvelzen.codingtest.data.api.OpenExchangeRatesApi
import com.rikvanvelzen.codingtest.data.api.RevolutApi
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyNamesDTO
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import io.reactivex.Observable

interface CurrencyRepository {

    fun getCurrencyRatesRx(baseCurrencyAbbreviation: String): Observable<CurrencyRatesDTO>
    fun getCurrencyNamesRx(): Observable<CurrencyNamesDTO>
}

class CurrencyRepositoryImpl(private val revolutApi: RevolutApi,
                             private val openExchangeRatesApi: OpenExchangeRatesApi) : CurrencyRepository {

    private var currencyNamesCache: CurrencyNamesDTO? = null

    /**************************************************
     * Public functions
     **************************************************/

    override fun getCurrencyRatesRx(baseCurrencyAbbreviation: String): Observable<CurrencyRatesDTO> {
        return revolutApi.getCurrencyRatesRx(baseCurrencyAbbreviation)
    }

    override fun getCurrencyNamesRx(): Observable<CurrencyNamesDTO> {

        if (currencyNamesCache != null) return Observable.just(currencyNamesCache)

        return openExchangeRatesApi.getCurrencyNamesRx()
                .map {

                    // map response into CurrencyNames object
                    val response = CurrencyNamesDTO(it).also { currencyNames ->
                        currencyNamesCache = currencyNames
                    }

                    response
                }
    }
}