package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.BuildConfig
import com.rikvanvelzen.codingtest.data.api.OpenExchangeRatesApi
import com.rikvanvelzen.codingtest.data.api.RevolutApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val REVOLUT_BASE_URL = "https://revolut.duckdns.org"
const val OPEN_EXCHANGE_RATES_BASE_URL = "https://openexchangerates.org/api/"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)

    @Singleton
    @Provides
    fun getRevolutApi(retrofit: Retrofit.Builder): RevolutApi = retrofit
            .baseUrl(REVOLUT_BASE_URL)
            .build().create(RevolutApi::class.java)

    @Singleton
    @Provides
    fun getOpenExchangeRatesApi(retrofit: Retrofit.Builder): OpenExchangeRatesApi = retrofit
            .baseUrl(OPEN_EXCHANGE_RATES_BASE_URL)
            .build().create(OpenExchangeRatesApi::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

        return OkHttpClient.Builder()
                .apply { if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor) }
                .build()
    }
}