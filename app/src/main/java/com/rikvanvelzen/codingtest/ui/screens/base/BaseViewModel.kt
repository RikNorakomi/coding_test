/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/23/20 12:24 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {


    @JvmField
    val isLoading = MutableLiveData<Boolean>()
//    val navigateBack = SingleLiveEvent<Any>()

    /******************************************************
     * Public methods
     */

//    fun onBackButtonClicked() {
//        navigateBack.call()
//    }
//
//    fun shouldNavigateBack(): LiveData<Any> {
//        return navigateBack
//    }
}
