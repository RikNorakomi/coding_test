/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:37 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies.converter

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rikvanvelzen.codingtest.common.kotlin.showKeyboard
import com.rikvanvelzen.codingtest.common.utils.StringFormatUtil
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.databinding.CurrencyItemBinding
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel
import java.math.BigDecimal

private const val TEXT_WATCHER_ALREADY_SET_TAG = "text watcher set!"

class VHCurrencyConverterItem(private val binding: CurrencyItemBinding,
                              private val viewModel: CurrencyViewModel,
                              private val lifecycleOwner: LifecycleOwner) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    companion object {
        val stringFormatUtil = StringFormatUtil()
    }

    private var exchangeRate: BigDecimal = 0.toBigDecimal()
    private lateinit var currency: Currency

    /**************************************************
     * Public functions
     **************************************************/

    fun bind(currency: Currency) {
        this.currency = currency

        binding.lifecycleOwner = lifecycleOwner
        binding.currency = currency
        binding.isFirstResponder = adapterPosition == 0
        binding.currencyRate.setOnClickListener { viewModel.onCurrencyItemClicked(currency, adapterPosition, exchangeRate) }
        binding.currencyItemContainer.setOnClickListener {

            viewModel.onCurrencyItemClicked(currency, adapterPosition, exchangeRate)
            binding.currencyRate.apply {

                isFocusableInTouchMode = true
                requestFocus()
                showKeyboard()
            }
        }

        // First responder currency amount is not calculated but taken and stored in property
        if (adapterPosition == 0) {
            setExchangeRate(stringFormatUtil.getFormattedExchangeRate(viewModel.baseCurrencyAmountLD.value))
        }

        setupObservers(currency)
        setupTextChangeListener()
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun setupObservers(currency: Currency) {

        viewModel.getExchangeRate(currency).observe(lifecycleOwner, Observer { rate ->

            if (this.adapterPosition != 0) {
                rate?.let { exchangeRate = it }
                setExchangeRate(stringFormatUtil.getFormattedExchangeRate(rate))
            }
        })
    }

    private fun setExchangeRate(rate: String) {
        binding.currencyRate.setText(rate)
    }

    private fun setupTextChangeListener() {

        // Make sure only 1 textChangeListener gets registered
        if (binding.currencyRate.tag == TEXT_WATCHER_ALREADY_SET_TAG) return

        binding.currencyRate.tag = TEXT_WATCHER_ALREADY_SET_TAG
        binding.currencyRate.doAfterTextChanged {

            // only inform viewModel of changes to first responder currency amount
            if (adapterPosition == 0) {
                viewModel.onBaseCurrencyAmountChanged(it.toString())
            }
        }
    }

}