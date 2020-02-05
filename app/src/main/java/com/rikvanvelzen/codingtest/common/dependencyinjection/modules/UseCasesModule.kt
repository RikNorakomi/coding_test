package com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.modules

import com.rikvanvelzen.tbocodingchallenge.data.repositories.BPIRatesRepository
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCaseImpl
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCase
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCaseImpl
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesHistoricalBPIRatesUseCase(bpiRatesRepository: BPIRatesRepository): CurrentRateUseCase = CurrentRateUseCaseImpl(bpiRatesRepository)

    @Provides
    fun providesBitcoinCurrentExchangeRateUseCase(bpiRatesRepository: BPIRatesRepository): CurrencyListUseCase = CurrencyListUseCaseImpl(bpiRatesRepository)
}