/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 10:17 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.modules;

import com.rikvanvelzen.codingtest.common.utils.StringFormatUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    StringFormatUtil getStringFormatUtil() {
        return new StringFormatUtil();
    }
}
