/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 6:26 PM
 */
package com.rikvanvelzen.codingtest.ui.components.bindingadapters

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

class EditTextViewBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("bind:placeCursorAtEndOnFocus")
        fun placeCursorAtEndOnFocus(editText: EditText, placeCursorAtEnd: Boolean) {

            if (placeCursorAtEnd) {

                editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->

                    if (hasFocus) {
                        (view as EditText).setSelection(view.text.length)
                    }
                }
            }
        }
    }
}