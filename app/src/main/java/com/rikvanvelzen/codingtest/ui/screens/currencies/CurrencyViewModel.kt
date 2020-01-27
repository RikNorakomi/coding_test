/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import com.rikvanvelzen.codingtest.kotlin.extensionfunctions.formatToTwoDecimals
import com.rikvanvelzen.codingtest.kotlin.extensionfunctions.isDecimalValueZero
import com.rikvanvelzen.codingtest.ui.screens.base.BaseViewModel
import io.reactivex.disposables.Disposable

class CurrencyViewModel : BaseViewModel() {

    private val currencyRepository = CurrencyRepository
    private var currencyData: MediatorLiveData<List<Currency>>? = null
    private var baseCurrencyAbbreviation = "EUR"
    var baseCurrencyAmount: Float = 100F


    private var mCurrenciesListDisposable: Disposable? = null // todo move to basevm
    private var currencyRatesDisposable: Disposable? = null // todo move to basevm

    private var currencyRates: MutableLiveData<CurrencyRates> = MutableLiveData()

    /**************************************************
     * Public functions
     **************************************************/

    fun getTabLayoutItems(): Array<CurrencyTabItems> = CurrencyTabItems.values()

    fun getExchangeRate(currency: Currency): LiveData<String> {
        return Transformations.map(currencyRates) {

            var amount: Float? = null
            it.rates[currency.abbreviation]?.let { rate ->
                amount = rate * baseCurrencyAmount
            }

            getFormattedExchangeRate(amount)
        }
    }

    fun getCurrencyData(): MutableLiveData<List<Currency>> {

        if (currencyData == null) {
            currencyData = MediatorLiveData()
            loadCurrencyData()
        }

        return currencyData as MediatorLiveData<List<Currency>>
    }

    /**************************************************
     * Private functions
     **************************************************/


    private fun loadCurrencyData() {

        mCurrenciesListDisposable = currencyRepository.getCurrencyListObservableRx(baseCurrencyAbbreviation)
                .doOnSubscribe { isLoading.value = true }
                .subscribe(
                        { currencies ->
                            isLoading.value = false
                            currencyData?.value = currencies
                        },
                        { error ->
                            // TODO
                            Log.e(TAG, "TODO Handle error!")
                        }
                )
                .also { disposables.add(it) }

        currencyRatesDisposable = currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation)
                .subscribe({

                    currencyRates.postValue(it)
                },
                        { error ->
                            // something went wrong
                            // TODO
                            Log.e(TAG, "TODO Handle error! Error:$error")

                        })
                .also { disposables.add(it) }
    }

    /**
     * Formats the currency's exchange rate:
     * - to empty string if it's null
     * - to NO decimals if decimals value is zero
     * - to 2 decimals in other cases
     */
    private fun getFormattedExchangeRate(rate: Float?): String {

        rate?.let {
            if (it.isDecimalValueZero()) return it.toInt().toString()

            return rate.formatToTwoDecimals()
        }

        return ""
    }
}


