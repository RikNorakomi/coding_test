/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/4/20 5:47 PM
 */

package com.rikvanvelzen.codingtest.domain

import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import io.reactivex.Observable

interface CurrentRateUseCase {

    fun getCurrentRates(baseCurrencyAbbreviation: String): Observable<CurrencyRates>
}

