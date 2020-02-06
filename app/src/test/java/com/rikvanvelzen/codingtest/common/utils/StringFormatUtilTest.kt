/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/5/20 10:43 PM
 */

package com.rikvanvelzen.codingtest.common.utils

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StringFormatUtilTest {

    private lateinit var SUT: StringFormatUtil

    @Before
    fun setup() {
        SUT = StringFormatUtil()
    }

    @Test
    fun getFormattedExchangeRate_nullValuePassed_emptyStringReturned() {
        // arrange
        val rate = null
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`(""))
    }

    @Test
    fun getFormattedExchangeRate_noDecimalPartOnFloatValue_intValueAsStringReturned() {
        // arrange
        val rate = 9.toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`(rate.toInt().toString()))
    }

    @Test
    fun getFormattedExchangeRate_decimalPartsValueIsZero_intValueAsStringReturned() {
        // arrange
        val rate = 88.0000.toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`(rate.toInt().toString()))
    }

    @Test
    fun getFormattedExchangeRate_singleDecimalValue_valueWithZeroAsSecondDecimalReturned() {
        // arrange
        val rate = 88.8.toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`("88.80"))
    }

    @Test
    fun getFormattedExchangeRate_twoDecimalsWithNotZeroValue_valueAsStringReturned() {
        // arrange
        val rate = 88.80.toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`("88.80"))
    }

    @Test
    fun getFormattedExchangeRate_thirdDecimalFiveOrHigher_valueWithTwoDecimalsRoundedUpAsStringReturned() {
        // arrange
        val rate = (88.805000000).toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`("88.81"))
    }

    @Test
    fun getFormattedExchangeRate_thirdDecimalBelowFive_valueWithTwoDecimalsNotRoundedUpAsStringReturned() {
        // arrange
        val rate = 88.804999999.toBigDecimal()
        // act
        val result = SUT.getFormattedExchangeRate(rate)
        // assert
        assertThat(result, `is`("88.80"))
    }
}