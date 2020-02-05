package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.data.api.CurrencyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val CURRENCY_BASE_URL = "https://revolut.duckdns.org"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(CURRENCY_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

    @Singleton
    @Provides
    fun getCurrencyService(retrofit: Retrofit): CurrencyApi = retrofit.create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }
}