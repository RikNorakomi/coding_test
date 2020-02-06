package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.data.api.OpenExchangeRatesApi
import com.rikvanvelzen.codingtest.data.api.RevolutApi
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesBPIRatesRepository(revolutApi: RevolutApi, openExchangeRatesApi: OpenExchangeRatesApi)
            : CurrencyRepository = CurrencyRepository(revolutApi, openExchangeRatesApi)
}