/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 5:31 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.modules;

import com.rikvanvelzen.codingtest.data.providers.CountryDataProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DataProviderModule {

    @Provides
    CountryDataProvider getCountryDataProvider() {
        return new CountryDataProvider();
    }
}
