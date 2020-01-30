/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:04 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.application;

import android.app.Application;

import com.rikvanvelzen.codingtest.data.api.CurrencyService;
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    CurrencyRepository getCurrencyRepository(CurrencyService currencyService) {
        return new CurrencyRepository(currencyService);
    }

}
