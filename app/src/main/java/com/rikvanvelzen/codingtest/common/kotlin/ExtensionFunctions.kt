/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 1:58 PM
 */

package com.rikvanvelzen.codingtest.common.kotlin

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.rikvanvelzen.codingtest.RevolutApplication
import java.text.DecimalFormat

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }

fun EditText.placeCursorToEnd() {
    this.setSelection(this.text.length)
}

fun CharSequence.toFloat(): Float?{
    return try {
        this.toString().toFloat()
    } catch (e: Exception) {
        null
    }
}

fun EditText.showKeyboard(forceKeyboard: Boolean = false) {
    val inputMethodManager = getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, if (forceKeyboard) InputMethodManager.SHOW_FORCED else InputMethodManager.SHOW_IMPLICIT)
}

fun Context.showToast(msg: String) {

    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)

    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
    v.gravity = Gravity.CENTER

    toast.show()
}

/**************************************************
 * Private functions
 **************************************************/

private fun getAppContext() = RevolutApplication.appContext


