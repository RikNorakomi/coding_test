package com.rikvanvelzen.codingtest.common.dependencyinjection.modules

import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyActivity
import com.rikvanvelzen.codingtest.common.dependencyinjection.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeCurrencyActivity(): CurrencyActivity
}
