/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:37 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies.allrates

import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.databinding.FragmentCurrencyRatesBinding
import com.rikvanvelzen.codingtest.ui.screens.base.MvvmBaseFragment
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel

/**
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 */
class CurrencyAllRatesFragment : MvvmBaseFragment<FragmentCurrencyRatesBinding, CurrencyViewModel>() {

    override fun getLayoutResource(): Int = R.layout.fragment_currency_rates
}