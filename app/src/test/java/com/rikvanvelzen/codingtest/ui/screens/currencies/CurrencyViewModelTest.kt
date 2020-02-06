/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/1/20 2:59 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.data.models.domain.CurrencyRates
import com.rikvanvelzen.codingtest.domain.CurrencyListUseCase
import com.rikvanvelzen.codingtest.domain.CurrentRateUseCase
import com.rikvanvelzen.tbocodingchallenge.common.getOrAwaitValue
import com.rikvanvelzen.tbocodingchallenge.common.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var currentRateUseCase: CurrentRateUseCase
    @Mock
    lateinit var currencyListUseCase: CurrencyListUseCase

    lateinit var SUT: CurrencyViewModel

    private val baseCurrencyAbbreviation = CURRENCY_ABBREVITAION_EURO

    @Before
    fun setup() {

        SUT = CurrencyViewModel(
                currentRateUseCase,
                currencyListUseCase,
                // Trampoline scheduler is great for testing.
                // It executes all tasks in a FIFO manner on one of the participating threads.
                Schedulers.trampoline(),
                Schedulers.trampoline())
    }

    @Test
    fun getTabLayoutItems_amountIsTwoOrThree() {
        // act
        val tabAmount = SUT.getTabLayoutItems().size
        // assert
        assertThat(tabAmount, anyOf(`is`(2), `is`(3)))
    }

    @Test
    fun getCurrencyData_getCurrencyListSuccess_liveDataUpdated() {

        // Arrange / Given
        val expectedResult = arrayListOf(getCurrencyItem1(), getCurrencyItem2())
        whenever(currentRateUseCase.getCurrentRates(anyString())).thenReturn(Observable.empty())

        // Act / When
        whenever(currencyListUseCase.getCurrencyList(anyString()))
                .thenReturn(Observable.just(expectedResult))
        SUT.getCurrencyData()

        // Assert
        assertEquals(SUT.currencyList?.getOrAwaitValue(), expectedResult)
    }

    @Test
    fun getCurrencyData_getCurrentRateSuccess_liveDataUpdated() {

        // Arrange / Given
        val expectedResult = getCurrencyRateItem()
        whenever(currencyListUseCase.getCurrencyList(anyString())).thenReturn(Observable.empty())

        // Act / When
        whenever(currentRateUseCase.getCurrentRates(baseCurrencyAbbreviation))
                .thenReturn(Observable.just(getCurrencyRateItem()))
        SUT.getCurrencyData()

        // Assert
        assertEquals(SUT.currencyRates.getOrAwaitValue(), expectedResult)
    }

    @Test
    fun loadCurrencyRates_getCurrentRatesReturnsError_errorLiveDataUpdated() {

        // Arrange / Given
        val errorResult = Throwable("error throwable")
        whenever(currencyListUseCase.getCurrencyList(anyString())).thenReturn(Observable.empty())

        // Act / When
        whenever(currentRateUseCase.getCurrentRates(anyString()))
                .thenReturn(Observable.error(errorResult))
        SUT.getCurrencyData()

        // Assert
        assertEquals(SUT.error.getOrAwaitValue(), errorResult)
    }


    @Test
    fun loadCurrencyData_getCurrencyListReturnsError_errorLiveDataUpdated() {

        // Arrange / Given
        val errorResult = Throwable("error throwable")
        whenever(currentRateUseCase.getCurrentRates(anyString())).thenReturn(Observable.empty())

        // Act / When
        whenever(currencyListUseCase.getCurrencyList(anyString()))
                .thenReturn(Observable.error(errorResult))
        SUT.getCurrencyData()

        // Assert
        assertEquals(SUT.error.getOrAwaitValue(), errorResult)
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun getCurrencyItem1(): Currency {
        return Currency(
                "abbreviation1",
                "name1",
                "countryCode1",
                "flagIconUrl1",
                1.toBigDecimal()
        )
    }

    private fun getCurrencyItem2(): Currency {
        return Currency(
                "abbreviation2",
                "name2",
                "countryCode2",
                "flagIconUrl2",
                2.toBigDecimal()
        )
    }

    private fun getCurrencyRateItem(): CurrencyRates {
        return CurrencyRates(
                mapOf("AUD1" to 1.2432.toBigDecimal()),
                baseCurrencyAbbreviation)
    }
}