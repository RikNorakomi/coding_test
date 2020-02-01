/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rikvanvelzen.codingtest.common.MultipleLiveDataTransformation
import com.rikvanvelzen.codingtest.common.SingleLiveEvent
import com.rikvanvelzen.codingtest.common.kotlin.default
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import com.rikvanvelzen.codingtest.ui.screens.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CurrencyViewModel : BaseViewModel() {

    init {
        getPresentationComponent().inject(this)
    }

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    private var currencyData: SingleLiveEvent<List<Currency>>? = null
    private var currencyRates: MutableLiveData<CurrencyRates> = MutableLiveData()
    private var currencyRatesDisposable: Disposable? = null
    private var baseCurrencyAbbreviation = "EUR"

    var itemPositionToMoveToTop = SingleLiveEvent<Int>()
    var baseCurrencyAmountLD = MutableLiveData<Double>().default(100.toDouble())
        private set

    /**************************************************
     * Public functions
     **************************************************/

    fun getTabLayoutItems(): Array<CurrencyTabItems> = CurrencyTabItems.values()

    fun getExchangeRate(currency: Currency): LiveData<Double?> {

        return MultipleLiveDataTransformation.biMap(currencyRates, baseCurrencyAmountLD)
        { ratesMap, baseCurrencyAmount ->

            var amount: Double? = null

            if (ratesMap != null && baseCurrencyAmount != null) {

                ratesMap.rates[currency.abbreviation]?.let { rate ->
                    amount = rate * baseCurrencyAmount
                }
            }

            amount
        }
    }

    fun getCurrencyData(): SingleLiveEvent<List<Currency>> {

        if (currencyData == null) {
            currencyData = SingleLiveEvent()
            loadCurrencyData()
            loadCurrencyRates()
        }

        return currencyData as SingleLiveEvent<List<Currency>>
    }

    fun onBaseCurrencyAmountChanged(amount: String?) {
        baseCurrencyAmountLD.value = amount?.toDoubleOrNull()
    }

    fun onCurrencyItemClicked(currency: Currency, adapterPosition: Int, exchangeRate: Double) {

        if (adapterPosition != 0) {

            currency.abbreviation?.let {
                baseCurrencyAbbreviation = it
                baseCurrencyAmountLD.value = exchangeRate
            }

            itemPositionToMoveToTop.postValue(adapterPosition)

            loadCurrencyRates()
        }
    }

    /**
     * This function takes care of only polling api for currency rates when converter screen is visible
     */
    fun onConverterScreenVisibilityChanged(visibleToUser: Boolean) {

        if (visibleToUser) {
            loadCurrencyRates()
        } else {
            currencyRatesDisposable?.let { disposables.remove(it) }
        }
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun loadCurrencyData() {

        currencyRepository.getCurrencyListObservableRx(baseCurrencyAbbreviation)
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
    }

    private fun loadCurrencyRates() {

        currencyRatesDisposable?.let { disposables.remove(it) }
        currencyRatesDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation) }
                .subscribe({
                    currencyRates.postValue(it)
                },
                        { error ->
                            // something went wrong
                            // TODO
                            Log.e(TAG, "TODO Handle error! Error:$error")

                        }).also {
                    disposables.add(it)
                }
    }
}


