/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:04 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.application;

import com.rikvanvelzen.codingtest.common.dependencyinjection.Constants;
import com.rikvanvelzen.codingtest.data.api.CurrencyService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.CURRENCY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    @Singleton
    @Provides
    CurrencyService getCurrencyService(Retrofit retrofit) {
        return retrofit.create(CurrencyService.class);
    }

}
