/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:05 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.presentation;

import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyActivity;
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel;
import com.rikvanvelzen.codingtest.ui.screens.currencies.converter.CurrencyConverterFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {

    void inject(CurrencyActivity currencyActivity);
    void inject(CurrencyConverterFragment currencyConverterFragment);
    void inject(CurrencyViewModel currencyViewModel);
}
