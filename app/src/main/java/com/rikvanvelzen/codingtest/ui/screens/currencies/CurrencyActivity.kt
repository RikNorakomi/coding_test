/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:19 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies

import android.os.Bundle
import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.databinding.ActivityCurrencyBinding
import com.rikvanvelzen.codingtest.ui.screens.base.MvvmBaseActivity
import com.rikvanvelzen.codingtest.ui.components.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : MvvmBaseActivity<ActivityCurrencyBinding, CurrencyViewModel>() {

    /**************************************************
     * Lifecycle functions
     **************************************************/

    override fun getLayoutResource(): Int = R.layout.activity_currency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun setupViews() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewModel.getTabLayoutItems().forEach { tabItem ->

            adapter.addFragment(tabItem.clazz.newInstance(), getString(tabItem.titleStringResId))
        }

        currency_activity_view_pager.apply {

            this.adapter = adapter
            currentItem = 1 // coding test only

            currency_activity_tab_layout.setupWithViewPager(this)
        }
    }
}