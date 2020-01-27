/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/23/20 12:24 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikvanvelzen.codingtest.helpers.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val TAG = javaClass.simpleName

    @JvmField
    val isLoading = MutableLiveData<Boolean>()
    val disposables: CompositeDisposable = CompositeDisposable()

    private val navigateBack = SingleLiveEvent<Any>()

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
