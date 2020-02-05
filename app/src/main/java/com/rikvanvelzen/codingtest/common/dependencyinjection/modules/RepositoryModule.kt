package com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.modules

import com.rikvanvelzen.tbocodingchallenge.data.api.BitcoinPriceIndexApi
import com.rikvanvelzen.tbocodingchallenge.data.repositories.BPIRatesRepository
import com.rikvanvelzen.tbocodingchallenge.data.repositories.BPIRatesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesBPIRatesRepository(bitcoinPriceIndexApi: BitcoinPriceIndexApi): BPIRatesRepository = BPIRatesRepositoryImpl(bitcoinPriceIndexApi)
}