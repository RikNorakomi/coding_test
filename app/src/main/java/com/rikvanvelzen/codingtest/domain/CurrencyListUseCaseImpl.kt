/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 3:02 PM
 */

package com.rikvanvelzen.codingtest.domain

import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyNamesDTO
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import com.rikvanvelzen.codingtest.data.providers.CountryDataProvider
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class CurrencyListUseCaseImpl(private val currencyRepository: CurrencyRepository,
                              private val countryDataProvider: CountryDataProvider) : CurrencyListUseCase {

    override fun getCurrencyList(baseCurrencyAbbreviation: String): Observable<ArrayList<Currency>> {

        return Observable.combineLatest<CurrencyNamesDTO, CurrencyRatesDTO, ArrayList<Currency>>(
                getCurrencyNamesObservable(),
                getCurrencyRatesObservable(baseCurrencyAbbreviation),
                BiFunction { currencyNames, currencyRates ->
                    combineCurrencyNamesAndRates(currencyNames, currencyRates, baseCurrencyAbbreviation)
                })
                .take(1)
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun getCurrencyNamesObservable(): Observable<CurrencyNamesDTO> = currencyRepository.getCurrencyNamesRx()

    private fun getCurrencyRatesObservable(baseCurrencyAbbreviation: String): Observable<CurrencyRatesDTO> = currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation)

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
}
