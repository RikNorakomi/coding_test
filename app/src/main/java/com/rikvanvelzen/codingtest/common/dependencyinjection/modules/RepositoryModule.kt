package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.data.api.CurrencyApi
import com.rikvanvelzen.codingtest.data.providers.CountryDataProvider
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesBPIRatesRepository(currencyApi: CurrencyApi)
            : CurrencyRepository = CurrencyRepository(currencyApi)
}