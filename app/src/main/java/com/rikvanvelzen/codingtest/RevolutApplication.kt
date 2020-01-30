/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:21 PM
 */

package com.rikvanvelzen.codingtest

import android.app.Application
import android.content.Context
import com.rikvanvelzen.codingtest.common.dependencyinjection.application.ApplicationComponent
import com.rikvanvelzen.codingtest.common.dependencyinjection.application.ApplicationModule
import com.rikvanvelzen.codingtest.common.dependencyinjection.application.DaggerApplicationComponent

class RevolutApplication : Application() {

    companion object {

        lateinit var applicationComponent: ApplicationComponent
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}