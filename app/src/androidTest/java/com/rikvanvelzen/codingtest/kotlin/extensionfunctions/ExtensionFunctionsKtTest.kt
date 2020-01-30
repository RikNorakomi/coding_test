/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/27/20 8:50 AM
 */

package com.rikvanvelzen.codingtest.kotlin.extensionfunctions

import com.rikvanvelzen.codingtest.common.kotlin.isDecimalValueZero
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class ExtensionFunctionsKtTest {

    // region constants

    // end region constants

    // region helper fields

    // end region helper fields


    @Before
    fun setup() {

    }

    @Test
    fun name() {

        assertThat(100.0f.isDecimalValueZero(), `is`(true))
    }

    // region helper methods

    // end region helper methods

    // region helper classes

    // end region helper classes
}