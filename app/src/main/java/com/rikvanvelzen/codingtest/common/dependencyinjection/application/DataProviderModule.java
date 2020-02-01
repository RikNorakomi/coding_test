/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/1/20 2:05 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.application;

import com.rikvanvelzen.codingtest.data.providers.CountryDataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataProviderModule {

    @Provides
    CountryDataProvider getCountryDataProvider() {
        return new CountryDataProvider();
    }
}
