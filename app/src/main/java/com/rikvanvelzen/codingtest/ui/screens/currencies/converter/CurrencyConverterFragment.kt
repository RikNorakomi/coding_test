/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:37 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies.converter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.databinding.CurrencyConverterFragmentBinding
import com.rikvanvelzen.codingtest.ui.screens.base.MvvmBaseFragment
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel

class CurrencyConverterFragment : MvvmBaseFragment<CurrencyConverterFragmentBinding, CurrencyViewModel>() {

    private lateinit var adapter: CurrencyConverterAdapter

    /**************************************************
     * Public functions
     **************************************************/

    override fun getLayoutResource(): Int = R.layout.currency_converter_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CurrencyConverterAdapter(viewModel, this)
        binding.fragmentCurrencyRecycler.adapter = adapter

        viewModel.getCurrencyData().observe(viewLifecycleOwner, Observer { adapter.setData(it) })
    }
}