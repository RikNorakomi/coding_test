package com.rikvanvelzen.codingtest.data

import com.rikvanvelzen.codingtest.data.api.CurrencyService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

//    private val CURRENCY_BASE_URL = "https://revolut.duckdns.org/latest?base=EUR"
    private val CURRENCY_BASE_URL = "https://revolut.duckdns.org"

    private val retrofitClientCurrency: Retrofit by lazy { getRetrofitClient(CURRENCY_BASE_URL) }

    /**************************************************
     * Public functions
     **************************************************/

    fun getCurrencyService(): CurrencyService = retrofitClientCurrency.create(CurrencyService::class.java)

    /**************************************************
     * Private functions
     **************************************************/

    private fun getRetrofitClient(baseUrl: String): Retrofit {

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())

        return retrofitBuilder.build()
    }
}