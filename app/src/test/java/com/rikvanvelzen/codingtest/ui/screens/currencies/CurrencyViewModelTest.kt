/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/1/20 2:59 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert
import org.junit.runner.RunWith
import org.mockito.Mock

import org.junit.Assert.assertThat
import org.junit.Rule
import org.mockito.Mockito.*

/******************************************************
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
class CurrencyViewModelTest {

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    // region constants

    // end region constants

    // region helper fields

    // end region helper fields

    lateinit var SUT: CurrencyViewModel

    @Before
    fun setup() {
        SUT = CurrencyViewModel()
    }

    @Test
    fun getTabLayoutItems_amountIsTwoOrThree() {
        // act
        val tabAmount = SUT.getTabLayoutItems().size
        // assert
        assertThat(tabAmount, anyOf(`is`(2), `is`(3)))
    }

    // region helper methods

    // end region helper methods

    // region helper classes

    // end region helper classes
}