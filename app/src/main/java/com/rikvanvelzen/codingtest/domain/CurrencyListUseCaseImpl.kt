/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 3:02 PM
 */

package com.rikvanvelzen.codingtest.domain

import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import io.reactivex.Observable

class CurrencyListUseCaseImpl(private val currencyRepository: CurrencyRepository) : CurrencyListUseCase {

    override fun getCurrencyList(baseCurrencyAbbreviation: String): Observable<ArrayList<Currency>> {

        // todo move mapping from repo to use case
        return currencyRepository.getCurrencyListObservableRx(baseCurrencyAbbreviation)
    }
}
