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
import com.rikvanvelzen.codingtest.common.SingleLiveEvent
import com.rikvanvelzen.codingtest.common.kotlin.toFloat
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import com.rikvanvelzen.codingtest.ui.screens.base.BaseViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class CurrencyViewModel : BaseViewModel() {

    init {
        getPresentationComponent().inject(this)
    }

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    private var currencyData: MediatorLiveData<List<Currency>>? = null
    private var currencyRates: MutableLiveData<CurrencyRates> = MutableLiveData()
    //    var baseCurrencyAmountLD = MutableLiveData<Float>().default(100F)
    var baseCurrencyAmount: Float? = 100F
    private var currencyRatesDisposable: Disposable? = null
    private var baseCurrencyAbbreviation = "EUR"

    //    var baseCurrencyAmount: Float = 100F
    var itemPositionToMoveToTop = SingleLiveEvent<Int>()

    /**************************************************
     * Public functions
     **************************************************/

    fun getTabLayoutItems(): Array<CurrencyTabItems> = CurrencyTabItems.values()

    fun getExchangeRate(currency: Currency): LiveData<Float?> {
//        return MultipleLiveDataTransformation.biMapNullSafe(currencyRates, baseCurrencyAmountLD
//        ) { ratesMap, baseCurrencyAmount ->
//            var amount: Float? = null
//
//            ratesMap.rates[currency.abbreviation]?.let { rate ->
//                amount = rate * baseCurrencyAmount
//            }
//            amount
//        }

        return Transformations.map(currencyRates) { currencyRates ->


            var amount: Float? = null

            baseCurrencyAmount?.let {

                currencyRates.rates[currency.abbreviation]?.let { rate ->
                    amount = rate * baseCurrencyAmount!!
                }
            }
            amount
        }
    }


    fun getCurrencyData(): MutableLiveData<List<Currency>> {

        if (currencyData == null) {
            currencyData = MediatorLiveData()
            loadCurrencyData()
            loadCurrencyRates()
        }

        return currencyData as MediatorLiveData<List<Currency>>
    }

    fun onBaseCurrencyAmountChanged(amount: CharSequence, start: Int, before: Int, count: Int) {
//        baseCurrencyAmountLD.value = amount.toString().toFloatOrNull()
//        baseCurrencyAmount = amount.toString().toFloat()

        Log.e(TAG, "amount= $amount")
    }

    fun onCurrencyItemClicked(currency: Currency, adapterPosition: Int, exchangeRate: Float) {

        if (adapterPosition != 0) {

            disposables.clear()

            currency.abbreviation?.let {
                baseCurrencyAbbreviation = it
                baseCurrencyAmount = exchangeRate
            }

            itemPositionToMoveToTop.postValue(adapterPosition)

            loadCurrencyRates()
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
        currencyRatesDisposable = currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation)
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


