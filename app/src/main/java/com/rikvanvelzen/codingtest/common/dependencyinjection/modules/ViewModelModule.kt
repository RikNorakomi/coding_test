package com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.ViewModelFactory
import com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.ViewModelKey
import com.rikvanvelzen.tbocodingchallenge.ui.screens.exchangerates.ExchangeRateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRateViewModel::class)
    abstract fun bindExchangeRateViewModel(viewModel: ExchangeRateViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}