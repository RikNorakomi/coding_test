/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyNames
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import com.rikvanvelzen.codingtest.kotlin.extensionfunctions.formatToTwoDecimals
import com.rikvanvelzen.codingtest.kotlin.extensionfunctions.isDecimalValueZero
import com.rikvanvelzen.codingtest.ui.screens.base.BaseViewModel
import io.reactivex.disposables.Disposable

class CurrencyViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    private val currencyRepository = CurrencyRepository
    private var currencyData: MediatorLiveData<List<Currency>>? = null
    private var currencyNames: MutableLiveData<List<CurrencyNames>> = MutableLiveData()
    private var baseCurrencyAbbreviation = "EUR"
    var baseCurrencyAmount: Float = 100F

    private var selectedCurrencyAbbr: MutableLiveData<String> = MutableLiveData()
    private var currencyRates: MutableLiveData<CurrencyRates> = MutableLiveData()

    /**************************************************
     * Public functions
     **************************************************/

    fun getTabLayoutItems(): Array<CurrencyTabItems> = CurrencyTabItems.values()

    fun onBackButtonClicked() {
        // not implemented for this code test
    }


    fun getExchangeRate(currency: Currency): LiveData<String> {
        return Transformations.map(currencyRates) {

            var amount: Float? = null
            it.rates[currency.abbreviation]?.let { rate ->
                amount = rate * baseCurrencyAmount
            }

            getFormattedCurrencyRate(amount)
        }
    }

    override fun onCleared() {
        super.onCleared()

        // todo add to compositeDIsposables
        mCurrenciesListDisposable?.dispose()
    }

    fun getCurrencyData(): MutableLiveData<List<Currency>> {

        if (currencyData == null) {
            currencyData = MediatorLiveData()
            loadCurrencyData()
        }

        return currencyData as MediatorLiveData<List<Currency>>
    }

    @SuppressLint("DefaultLocale")
    fun getCountryFlagIconUrl(currency: Currency): String? {

        var countryCode = currency.abbreviation?.substring(0, 2)?.toLowerCase()
        if (countryCode == "eu"){
            countryCode = "european_union"
        }
        return "https://hatscripts.github.io/circle-flags/flags/$countryCode.svg"
    }

    /**************************************************
     * Private functions
     **************************************************/

    private var mCurrenciesListDisposable: Disposable? = null
    private var currencyRatesDisposable: Disposable? = null

    private fun loadCurrencyData() {

        mCurrenciesListDisposable = currencyRepository.getCurrencyListObservableRx(baseCurrencyAbbreviation)
                .doOnSubscribe { isLoading.value = true }
                .subscribe(
                        { currencies ->
                            isLoading.value = false
                            currencyData?.value = currencies
                        },
                        { error ->
                            Log.e(TAG, "TODO Handle error!")
                        }
                )

        currencyRatesDisposable = currencyRepository.getCurrencyRatesRx(baseCurrencyAbbreviation)
                .subscribe({

                    currencyRates.postValue(it) },
                        { error ->
                            // something went wrong
                            // TODO
                            Log.e(TAG, "TODO Handle error! Error:$error")

                        })


//        currencyRepository.getCurrencyRates(baseCurrency).observeForever {
//            currencyData?.value = it


//                result?.let {
//
//                    Logging.logError(TAG, "loadAudioTracks: bookmark / result state = " + it.status)
//                    isLoading.value = it.status == LOADING
//                    if (it.status == SUCCESS) {
//
//                        Logging.logError(TAG, "loadAudioTracks: size pre filter: " + it.data?.size)
//
//                        val audioTracks = AudioRepository.getFilteredAudioTracks(getCurrentAudioTrackFilter())
//
//                        Logging.logError(TAG, "loadAudioTracks: size post filter: " + audioTracks?.size)
//                        this.audioTracks?.postValue(audioTracks)
//                        hasErrorLoadingData.value = false
//                    }
//
//                    if (it.status == ERROR) {
//                        onErrorGetAudioTracks(it.message, it.errorCode)
//                    }
//                }
//        }

//        currencyRepository.getCurrencyNames().observeForever {
//            currencyNames.value = it
//        }

    }

    /**
     * Formats the currency rate:
     * - to empty string if it's null
     * - to NO decimals if decimals value is zero
     * - to 2 decimals in other cases
     */
    private fun getFormattedCurrencyRate(rate: Float?): String {

        rate?.let {
            if (it.isDecimalValueZero()) return it.toInt().toString()

            return rate.formatToTwoDecimals()
        }

        return ""
    }
}


