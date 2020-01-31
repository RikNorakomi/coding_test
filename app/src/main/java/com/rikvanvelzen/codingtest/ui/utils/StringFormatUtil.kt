/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/31/20 4:13 PM
 */
package com.rikvanvelzen.codingtest.ui.utils

import com.rikvanvelzen.codingtest.common.kotlin.formatToTwoDecimals
import com.rikvanvelzen.codingtest.common.kotlin.isDecimalValueZero

class StringFormatUtil {

    /**
     * Formats the currency's exchange rate:
     * - to empty string if it's null
     * - to NO decimals if decimals value is zero
     * - to 2 decimals in other cases
     */
    fun getFormattedExchangeRate(rate: Float?): String {

        rate?.let {
            if (it.isDecimalValueZero()) return it.toInt().toString()

            return rate.formatToTwoDecimals()
        }

        return ""
    }
}