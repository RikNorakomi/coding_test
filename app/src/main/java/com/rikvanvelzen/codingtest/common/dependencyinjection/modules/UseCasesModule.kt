package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCaseImpl
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCase
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCaseImpl
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesCurrencyListUseCase(currencyRepository: CurrencyRepository): CurrencyListUseCase = CurrencyListUseCaseImpl(currencyRepository)

    @Provides
    fun providesCurrentRateUseCase(currencyRepository: CurrencyRepository): CurrentRateUseCase = CurrentRateUseCaseImpl(currencyRepository)
}