package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel
import com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.ViewModelFactory
import com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    abstract fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}