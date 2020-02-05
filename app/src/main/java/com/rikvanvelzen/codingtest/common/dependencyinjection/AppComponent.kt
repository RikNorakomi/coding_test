/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/4/20 1:30 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection

import com.rikvanvelzen.codingtest.RevolutApplication
import com.rikvanvelzen.codingtest.common.dependencyinjection.modules.ActivityBindingModule
import com.rikvanvelzen.codingtest.common.dependencyinjection.modules.FragmentBindingModule
import com.rikvanvelzen.tbocodingchallenge.common.dependencyinjection.modules.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityBindingModule::class,
            FragmentBindingModule::class,
            RepositoryModule::class,
            NetworkModule::class,
            ApplicationModule::class,
            UseCasesModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent : AndroidInjector<RevolutApplication>

