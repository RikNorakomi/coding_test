/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/31/20 4:27 PM
 */
package com.rikvanvelzen.codingtest.ui.screens.currencies

import android.view.View
import com.rikvanvelzen.codingtest.RevolutApplication
import com.rikvanvelzen.codingtest.common.DIold.application.ApplicationComponent
import com.rikvanvelzen.codingtest.common.DIold.presentation.PresentationComponent
import com.rikvanvelzen.codingtest.common.DIold.presentation.PresentationModule

open class BaseViewHolder(root: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(root){

    private var mIsInjectorUsed: Boolean = false

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