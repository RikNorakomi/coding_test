/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:04 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.presentation;

import com.rikvanvelzen.codingtest.ui.utils.StringFormatUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    @Provides
    StringFormatUtil getStringFormatUtil() {
        return new StringFormatUtil();
    }
}
