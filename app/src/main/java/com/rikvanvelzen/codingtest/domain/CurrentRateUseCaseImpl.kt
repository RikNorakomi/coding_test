/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 5:16 PM
 */

package com.rikvanvelzen.codingtest.domain

import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import io.reactivex.Observable

class CurrentRateUseCaseImpl(private val currencyRepository: CurrencyRepository) : CurrentRateUseCase {

    override fun getCurrentRates(baseCurrencyAbbreviation: String): Observable<CurrencyRates> {

        return currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation)
    }

}

