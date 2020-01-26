/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:19 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import androidx.fragment.app.Fragment
import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.ui.screens.currencies.allrates.CurrencyAllRatesFragment
import com.rikvanvelzen.codingtest.ui.screens.currencies.converter.CurrencyConverterFragment

enum class CurrencyTabItems(var titleStringResId: Int, var clazz: Class<out Fragment>) {

    ALL_RATES(R.string.currencies_tab_all_rates, CurrencyAllRatesFragment::class.java),
    CONVERTER(R.string.currencies_tab_converter, CurrencyConverterFragment::class.java);
}