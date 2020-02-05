/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/23/20 12:24 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikvanvelzen.codingtest.RevolutApplication
import com.rikvanvelzen.codingtest.common.SingleLiveEvent
import com.rikvanvelzen.codingtest.common.DIold.application.ApplicationComponent
import com.rikvanvelzen.codingtest.common.DIold.presentation.PresentationComponent
import com.rikvanvelzen.codingtest.common.DIold.presentation.PresentationModule
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val TAG = javaClass.simpleName

    val isLoading = MutableLiveData<Boolean>()
    val disposables: CompositeDisposable = CompositeDisposable()

    private val navigateBack = SingleLiveEvent<Any>()
    private var mIsInjectorUsed = false

    /**************************************************
     * Lifecycle functions
     **************************************************/

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
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

    fun getPresentationComponent(): PresentationComponent {
        if (mIsInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        mIsInjectorUsed = true
        return getApplicationComponent().newPresentationComponent(PresentationModule())
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun getApplicationComponent(): ApplicationComponent {
        return RevolutApplication.applicationComponent
    }
}
