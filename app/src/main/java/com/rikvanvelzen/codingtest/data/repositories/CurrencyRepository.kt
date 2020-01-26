/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:32 PM
 */

package com.rikvanvelzen.codingtest.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rikvanvelzen.codingtest.data.RetrofitManager
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyNames
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyNamesDTO
import com.rikvanvelzen.codingtest.data.models.dto.CurrencyRatesDTO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object CurrencyRepository {

    private val TAG = javaClass.simpleName

    private val currencyService by lazy { RetrofitManager.getCurrencyService() }

    private var currencyData = MutableLiveData<List<Currency>>()
    private var currencyNames = MutableLiveData<List<CurrencyNames>>()
    private var currencyNamesCache: CurrencyNamesDTO? = null

    private val disposables = CompositeDisposable()

    //https://www.countryflags.io/be/flat/64.png
    private val countryFlagUrl = "https://www.countryflags.io/:country_code/flat/64.png"

    /**************************************************
     * Public functions
     **************************************************/

    fun getCurrencyRatesRx(baseCurrencyAbbreviation: String): Observable<CurrencyRates> {

        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { currencyService.getCurrencyRatesRx(baseCurrencyAbbreviation) }
                .map {
//                    Log.e(TAG, "mapping")
                    CurrencyRates(it.rates ?: emptyMap(), baseCurrencyAbbreviation)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCurrencyListObservableRx(baseCurrencyAbbreviation: String): Observable<ArrayList<Currency>> {

        val namesObservable = getCurrencyNamesObservable(baseCurrencyAbbreviation)
        val ratesObservable = getRatesObservable(baseCurrencyAbbreviation)

//        disposables.add(namesObservable) // TODO

        return Observable.combineLatest<CurrencyNamesDTO, CurrencyRatesDTO, ArrayList<Currency>>(
                namesObservable,
                ratesObservable,
                BiFunction { currencyNames, currencyRates ->
                    combineCurrencyNamesAndRates(currencyNames, currencyRates, baseCurrencyAbbreviation)
                })
                .observeOn(AndroidSchedulers.mainThread())
    }


    fun getCurrencyRates(baseCurrency: String): LiveData<List<Currency>> {

        val call = currencyService.getCurrencyRates(baseCurrency)
        call.enqueue(object : Callback<CurrencyRatesDTO> {

            override fun onResponse(call: Call<CurrencyRatesDTO>, response: Response<CurrencyRatesDTO>) {
                if (response.isSuccessful) {

                    response.body()?.rates?.let {

                        val currencyList = ArrayList<Currency>()
                        currencyList.addAll(it.map { currencyData ->
                            Currency(currencyData.key, null, null, currencyData.value)
                        })

                        currencyData.value = currencyList
                    }

                    Log.e(TAG, "onResponse. size = " + response.body()?.rates + "" +
                            "\nraw=" + response.raw() +
                            "\ncode=" + response.code()
                    )
                }
            }

            override fun onFailure(call: Call<CurrencyRatesDTO>, t: Throwable) {

                Log.e(TAG, "onFailure " + t)
            }
        })

        return currencyData
    }

    fun getCurrencyNames(): LiveData<List<CurrencyNames>> {

        val call = currencyService.getCurrencyNames()
        call.enqueue(object : Callback<CurrencyNamesDTO> {

            override fun onResponse(call: Call<CurrencyNamesDTO>, response: Response<CurrencyNamesDTO>) {
                if (response.isSuccessful) {

                    response.body()?.names?.let {

                        val currencyList = ArrayList<CurrencyNames>()
                        currencyList.addAll(it.map { currency ->
                            CurrencyNames(currency.key, currency.value)
                        })

                        currencyNames.value = currencyList
                    }

                    Log.e(TAG, "onResponse. size = " + response.body()?.names + "" +
                            "\nraw=" + response.raw() +
                            "\ncode=" + response.code()
                    )
                }
            }

            override fun onFailure(call: Call<CurrencyNamesDTO>, t: Throwable) {

                Log.e(TAG, "onFailure " + t)
            }
        })

        return currencyNames
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun combineCurrencyNamesAndRates(
            namesResponse: CurrencyNamesDTO,
            ratesResponse: CurrencyRatesDTO,
            baseCurrency: String): ArrayList<Currency> {

        val currencies = ArrayList<Currency>()

        // add baseCurrency as first item
        val name = namesResponse.names?.get(baseCurrency)
        currencies.add(Currency(
                baseCurrency,
                name,
                getCountryCodeFromName(name),
                1F
        ))

        ratesResponse.rates?.forEach { (abbreviation, rate) ->

            //            Log.e(TAG, "rate = $rate")
            val name = namesResponse.names?.get(abbreviation)
            currencies.add(
                    Currency(
                            abbreviation,
                            name,
                            getCountryCodeFromName(name),
                            rate
                    ))
        }

        return currencies
    }

    val isoCountries = Locale.getISOCountries()

//    private fun getCountryCodeFromName(name: String?): String? {
//
//        val find = isoCountries.find { Locale("", it).displayCountry == name }
//
//        CountryPicker.instantiate()
////        Log.e(TAG, "name=$name found=$find")
//        return find
//    }

    fun getCountryCodeFromName(countryName: String?): String? {
        val countries: MutableMap<String, String> = HashMap()
        for (iso in Locale.getISOCountries()) {
            val l = Locale("", iso)
            countries[l.displayCountry] = iso
        }
        val isoCode = countries[countryName]

//        Log.e(TAG, "name=$countryName found=$isoCode")
        return isoCode
    }

    private fun getCurrencyNamesObservable(baseCurrency: String): Observable<CurrencyNamesDTO> {

        if (currencyNamesCache != null) return Observable.just(currencyNamesCache)

        return currencyService.getCurrencyNamesRx()
                .map {

                    // map response into CurrencyNames object
                    val response = CurrencyNamesDTO(it).also { currencyNames ->
                        currencyNamesCache = currencyNames
                    }

                    response
                }
                .subscribeOn(Schedulers.io()) // todo inject scheduler
    }


    private fun getRatesObservable(baseCurrency: String): Observable<CurrencyRatesDTO>? {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { currencyService.getCurrencyRatesRx(baseCurrency) }
                .subscribeOn(Schedulers.io())


//        val currencyRatesObservable = Observable.combineLatest<Currency, Long, String>(
//                mBaseCurrencySubject,
//                Observable.interval(0, 1, TimeUnit.SECONDS),
//                BiFunction { currency, long -> currency.code!! })
//                .flatMap { mCurrenciesUseCase.getRates(getBaseCurrency().code!!) }
//        return currencyRatesObservable
    }
}