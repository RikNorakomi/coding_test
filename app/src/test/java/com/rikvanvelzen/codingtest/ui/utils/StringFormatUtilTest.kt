/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/1/20 2:07 PM
 */

package com.rikvanvelzen.codingtest.ui.utils

import com.rikvanvelzen.codingtest.common.utils.StringFormatUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/*****************************************************
 * <unitOfWork>_<stateUnderTest>_<expectedBehavior>
 *
 * - camelCased
 * - single underscore separated parts
 *
 * - unitOfWork         =>  most cases a method name. Can also be general operation or flow
 * - expectedBehavior   =>  result/behavior when the test completes. Can be retun value,
 *                          thrown exception, external interaction, etc.
 *
 * Nullability: - Assumptions in all test => method arguments and return values are non-null (?!) by default
 *              - Nullable values will explicitly be annotated with @Null
 *              - No need to test nulls in most(!) cases
 * Boundary Conditions: make sure all boundary conditions are tested
 *
 ******************************************************/
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