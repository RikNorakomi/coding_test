/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.data.repositories

import com.rikvanvelzen.codingtest.data.api.CurrencyService
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyNamesDTO
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import com.rikvanvelzen.codingtest.data.providers.CountryDataProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyRepository(private val currencyService: CurrencyService) {

    private val countryDataProvider by lazy { CountryDataProvider }
    private var currencyNamesCache: CurrencyNamesDTO? = null

    /**************************************************
     * Public functions
     **************************************************/

    fun getCurrencyRatesRx(baseCurrencyAbbreviation: String): Observable<CurrencyRates> {
        return currencyService.getCurrencyRatesRx(baseCurrencyAbbreviation)
                .map {
                    CurrencyRates(it.rates ?: emptyMap(), baseCurrencyAbbreviation)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCurrencyListObservableRx(baseCurrencyAbbreviation: String): Observable<ArrayList<Currency>> {

        val namesObservable = getCurrencyNamesObservable()
        val ratesObservable = getRatesObservable(baseCurrencyAbbreviation)

        return Observable.combineLatest<CurrencyNamesDTO, CurrencyRatesDTO, ArrayList<Currency>>(
                namesObservable,
                ratesObservable,
                BiFunction { currencyNames, currencyRates ->
                    combineCurrencyNamesAndRates(currencyNames, currencyRates, baseCurrencyAbbreviation)
                })
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun combineCurrencyNamesAndRates(
            namesResponse: CurrencyNamesDTO,
            ratesResponse: CurrencyRatesDTO,
            baseCurrencyAbbreviation: String): ArrayList<Currency> {

        val currencies = ArrayList<Currency>()

        // add baseCurrency as first item
        val baseCurrencyName = namesResponse.names?.get(baseCurrencyAbbreviation)
        currencies.add(Currency(
                baseCurrencyAbbreviation,
                baseCurrencyName,
                countryDataProvider.getCountryCodeFromName(baseCurrencyName),
                countryDataProvider.getCountryFlagUrl(baseCurrencyAbbreviation),
                1.toBigDecimal()
        ))

        ratesResponse.rates?.forEach { (abbreviation, rate) ->

            val name = namesResponse.names?.get(abbreviation)
            currencies.add(
                    Currency(
                            abbreviation,
                            name,
                            countryDataProvider.getCountryCodeFromName(name),
                            countryDataProvider.getCountryFlagUrl(abbreviation),
                            rate
                    ))
        }

        return currencies
    }


    private fun getCurrencyNamesObservable(): Observable<CurrencyNamesDTO> {

        if (currencyNamesCache != null) return Observable.just(currencyNamesCache)

        return currencyService.getCurrencyNamesRx()
                .map {

                    // map response into CurrencyNames object
                    val response = CurrencyNamesDTO(it).also { currencyNames ->
                        currencyNamesCache = currencyNames
                    }

                    response
                }
                .subscribeOn(Schedulers.io())
    }


    private fun getRatesObservable(baseCurrency: String): Observable<CurrencyRatesDTO>? {

        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { currencyService.getCurrencyRatesRx(baseCurrency) }
                .subscribeOn(Schedulers.io())
    }
}