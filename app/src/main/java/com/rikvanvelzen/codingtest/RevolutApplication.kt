/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:21 PM
 */

package com.rikvanvelzen.codingtest

import android.app.Application
import android.content.Context
import com.rikvanvelzen.codingtest.common.dependencyinjection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RevolutApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {

        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        DaggerAppComponent.create().inject(this)
    }
}