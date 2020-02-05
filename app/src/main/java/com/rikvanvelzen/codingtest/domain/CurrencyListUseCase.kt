/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 3:05 PM
 */

package com.rikvanvelzen.codingtest.domain

import com.rikvanvelzen.codingtest.data.models.domain.Currency
import io.reactivex.Observable

interface CurrencyListUseCase {

    fun getCurrencyList(baseCurrencyAbbreviation: String):
            Observable<ArrayList<Currency>>
}