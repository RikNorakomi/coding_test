/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/23/20 12:24 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.base

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikvanvelzen.codingtest.RevolutApplication
import com.rikvanvelzen.codingtest.common.dependencyinjection.application.ApplicationComponent
import com.rikvanvelzen.codingtest.common.dependencyinjection.presentation.PresentationComponent
import com.rikvanvelzen.codingtest.common.dependencyinjection.presentation.PresentationModule
import com.rikvanvelzen.codingtest.helpers.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val TAG = javaClass.simpleName

    @JvmField
    val isLoading = MutableLiveData<Boolean>()
    val disposables: CompositeDisposable = CompositeDisposable()

    private val navigateBack = SingleLiveEvent<Any>()

    private var mIsInjectorUsed = false

    @UiThread
    protected open fun getPresentationComponent(): PresentationComponent {
        if (mIsInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        mIsInjectorUsed = true
        return getApplicationComponent().newPresentationComponent(PresentationModule())
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return RevolutApplication.applicationComponent
    }

    /**************************************************
     * Public functions
     **************************************************/

    fun onBackButtonClicked() {
        navigateBack.call()
    }

    fun shouldNavigateBack(): LiveData<Any> {
        return navigateBack
    }

    /**************************************************
     * Liefcycle functions
     **************************************************/

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
