/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:37 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies.converter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.databinding.CurrencyItemBinding
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel

class VHCurrencyConverterItem(private val binding: CurrencyItemBinding,
                              private val viewModel: CurrencyViewModel,
                              private val lifecycleOwner: LifecycleOwner) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    private val TAG = javaClass.simpleName

    fun bind(currency: Currency, function: () -> Unit) {

        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
        binding.currency = currency

        binding.currencyItemContainer.setOnClickListener { function.invoke() }


        setupObservers(currency)
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun setupObservers(currency: Currency) {

        Log.e(TAG, "adapterPosition=$adapterPosition layoutPosition=$layoutPosition")
        if (this.layoutPosition == 0){
            setExchangeRate(viewModel.baseCurrencyAmount.toString())
        } else {
            viewModel.getExchangeRate(currency).observe(lifecycleOwner, Observer {
                setExchangeRate(it)
            })
        }
    }

    private fun setExchangeRate(rate: String){
        binding.currencyRate.setText(rate)
    }
}