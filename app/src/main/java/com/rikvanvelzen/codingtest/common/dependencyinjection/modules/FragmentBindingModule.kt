package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.ui.screens.currencies.allrates.CurrencyAllRatesFragment
import com.rikvanvelzen.codingtest.ui.screens.currencies.converter.CurrencyConverterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

  @ContributesAndroidInjector
  abstract fun contributeCurrencyConverterFragment(): CurrencyConverterFragment

  @ContributesAndroidInjector
  abstract fun contributeCurrencyAllRatesFragment(): CurrencyAllRatesFragment
}
