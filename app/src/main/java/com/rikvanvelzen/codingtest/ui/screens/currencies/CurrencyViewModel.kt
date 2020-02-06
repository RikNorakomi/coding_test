/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rikvanvelzen.codingtest.common.MultipleLiveDataTransformation
import com.rikvanvelzen.codingtest.common.SingleLiveEvent
import com.rikvanvelzen.codingtest.common.dependencyinjection.modules.SCHEDULER_IO
import com.rikvanvelzen.codingtest.common.dependencyinjection.modules.SCHEDULER_MAIN_THREAD
import com.rikvanvelzen.codingtest.common.kotlin.default
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCase
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCase
import com.rikvanvelzen.codingtest.ui.screens.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

const val CURRENCY_ABBREVITAION_EURO = "EUR"

class CurrencyViewModel
@Inject constructor(
        private val currencyRatesUseCase: CurrentRateUseCase,
        private val currencyDataUseCase: CurrencyListUseCase,
        @Named(SCHEDULER_IO) val subscribeOnScheduler: Scheduler,
        @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler) : BaseViewModel() {

    val currencyRates: MutableLiveData<CurrencyRates> = MutableLiveData()
    var currencyList: MutableLiveData<List<Currency>>? = null
    var error = MutableLiveData<Throwable>()
    var errorSnackbar = SingleLiveEvent<Unit>()
    private var baseCurrencyAbbreviation = CURRENCY_ABBREVITAION_EURO
    private var currencyRatesDisposable: Disposable? = null

    var itemPositionToMoveToTop = SingleLiveEvent<Int>()
    var baseCurrencyAmountLD = MutableLiveData<BigDecimal>().default(100.toBigDecimal())
        private set

    /**************************************************
     * Public functions
     **************************************************/

    fun getTabLayoutItems(): Array<CurrencyTabItems> = CurrencyTabItems.values()

    fun getExchangeRate(currency: Currency): LiveData<BigDecimal?> {

        return MultipleLiveDataTransformation.biMap(currencyRates, baseCurrencyAmountLD)
        { ratesMap, baseCurrencyAmount ->

            var amount: BigDecimal? = null

            if (ratesMap != null && baseCurrencyAmount != null) {

                ratesMap.rates[currency.abbreviation]?.let { rate ->
                    amount = baseCurrencyAmount.multiply(rate)
                }
            }

            amount
        }
    }

    fun getCurrencyData(): MutableLiveData<List<Currency>> {

        if (currencyList == null) {
            currencyList = MutableLiveData()
            loadData()
        }

        return currencyList as MutableLiveData<List<Currency>>
    }

    fun onBaseCurrencyAmountChanged(amount: String?) {
        baseCurrencyAmountLD.value = amount?.toBigDecimalOrNull()
    }

    fun onCurrencyItemClicked(currency: Currency, adapterPosition: Int, exchangeRate: BigDecimal) {

        if (adapterPosition != 0) {

            currency.abbreviation?.let {
                baseCurrencyAbbreviation = it
                baseCurrencyAmountLD.value = exchangeRate
            }

            itemPositionToMoveToTop.postValue(adapterPosition)

            loadCurrencyRates()
        }
    }

    fun loadData() {
        loadCurrencyData()
        loadCurrencyRates()
    }

    /**
     * This function takes care of only polling api for currency rates when converter screen is visible
     */
    fun onConverterScreenVisibilityChanged(visibleToUser: Boolean) {

        if (visibleToUser) {
            loadCurrencyRates()
        } else {
            currencyRatesDisposable?.dispose()
        }
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun loadCurrencyData() {

        currencyDataUseCase.getCurrencyList(baseCurrencyAbbreviation)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .take(1)
                .doOnSubscribe { isLoading.value = true }
                .doAfterTerminate { isLoading.postValue(false) }
                .subscribe(
                        { currencies -> currencyList?.value = currencies },
                        { errorSnackbar.call() })
                .also { disposables.add(it) }
    }

    private fun loadCurrencyRates() {

        currencyRatesDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { currencyRatesUseCase.getCurrentRates(baseCurrencyAbbreviation) }
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe({ currencyRates.postValue(it) }, {
                    error.value = it
                })
                .also { disposables.add(it) }
    }
}


