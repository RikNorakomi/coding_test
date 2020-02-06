/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 2/1/20 3:35 PM
 */

package com.rikvanvelzen.codingtest.data.providers

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryDataProviderTest {

    private val baseUrl = COUNTRY_FLAGS_URL
    private val fileType = IMAGE_FILE_TYPE
    private val countryCodeEU = COUNTRY_CODE_EU

    private lateinit var SUT: CountryDataProvider

    @Before
    fun setup() {
        SUT = CountryDataProvider()
    }

    @Test
    fun getCountryFlagUrl_currencyAbbreviationLengthZeroOrOne_nullReturned() {
        // arrange
        val currencyAbbrCharLengthZero = ""
        val currencyAbbrCharLengthOne = "a"
        // act
        val resultLengthZero = SUT.getCountryFlagUrl(currencyAbbrCharLengthZero)
        val resultLengthOne = SUT.getCountryFlagUrl(currencyAbbrCharLengthOne)
        // assert
        assertThat(resultLengthZero, `is`(nullValue()))
        assertThat(resultLengthOne, `is`(nullValue()))
    }

    @Test
    fun getCountryFlagUrl_currencyAbbreviationUppercase_lowerCaseCountryCodeInUrlReturned() {
        // arrange
        val currencyAbbr = "CAD"
        // act
        val url = SUT.getCountryFlagUrl(currencyAbbr)
        val countryCode = getCountryCodeFromUrl(url)
        // assert
        assertThat(countryCode.hasUppercase(), `is`(false))
    }

    @Test
    fun getCountryFlagUrl_currencyAbbreviationWithMoreThanTwoChars_countryCodeHasTwoLettersInUrlReturned() {
        // arrange
        val currencyAbbr = "CAD"
        // act
        val url = SUT.getCountryFlagUrl(currencyAbbr)
        val countryCode = getCountryCodeFromUrl(url)
        // assert
        assertEquals(countryCode.length, 2)
    }

    @Test
    fun getCountryFlagUrl_currencyAbbreviationEU_urlWithEuropeanUnionCountryCodeReturned() {
        // arrange
        val currencyAbbr = "EU"
        // act
        val url = SUT.getCountryFlagUrl(currencyAbbr)
        val countryCode = getCountryCodeFromUrl(url)

        // assert
        assertEquals(countryCode, countryCodeEU)
    }

    @Test
    fun getCountryCurrencyCodeFromName_currencyNameNull_nullReturned() {
        val result = SUT.getCountryCodeFromName(null)
        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun getCountryCodeFromName_countryNameUnitedStates_USReturned() {
        // arrange
        val countryNameGermany = "Germany"
        // act
        val resultGermany = SUT.getCountryCodeFromName(countryNameGermany)
        // assert
        assertEquals(resultGermany, "DE")
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun getCountryCodeFromUrl(url: String?): String {

        if (url == null) throw IllegalStateException("url is null!")

        return url.replace(baseUrl, "")
                .replace(fileType, "")
    }

    private fun String.hasUppercase(): Boolean {
        return this != this.toLowerCase()
    }
}