/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/31/20 4:13 PM
 */
package com.rikvanvelzen.codingtest.ui.utils

import java.text.DecimalFormat

class StringFormatUtil {

    private val formatter = DecimalFormat("0.00")

    /**
     * Formats the currency's exchange rate:
     * - to empty string if it's null
     * - to NO decimals if decimals value is zero
     * - to 2 decimals in other cases
     */
    fun getFormattedExchangeRate(rate: Double?): String {

        rate?.let {
            if (it.isDecimalValueZero()) return it.toInt().toString()

            return rate.formatToTwoDecimals()
        }

        return ""
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun Double.formatToTwoDecimals(): String {

        return formatter.format(this)
    }

    private fun Double.isDecimalValueZero(): Boolean {
        val integer = this.toInt()
        val decimal = this - integer.toDouble()
        return decimal == 0.toDouble()
    }
}