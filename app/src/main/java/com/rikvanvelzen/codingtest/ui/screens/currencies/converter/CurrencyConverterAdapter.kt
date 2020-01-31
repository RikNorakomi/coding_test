/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 4:37 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.currencies.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.data.models.domain.Currency
import com.rikvanvelzen.codingtest.databinding.CurrencyItemBinding
import com.rikvanvelzen.codingtest.ui.screens.currencies.CurrencyViewModel
import java.util.*
import java.util.Collections.swap

class CurrencyConverterAdapter(var viewModel: CurrencyViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = javaClass.simpleName

    private lateinit var recyclerView: RecyclerView
    private var currencies: ArrayList<Currency> = ArrayList()

    /*********************************
     * Public functions
     *********************************/

    fun setData(data: List<Currency>) {
        if (currencies.size > 0) return // todo remove after figuring out right pattern to update rates

        currencies.clear()
        currencies.addAll(data)

        notifyDataSetChanged()
    }

    fun swapItemToTop(itemPosition: Int){

        // do nothing if item is already at top
        if (itemPosition == 0) return

        // move item to top and scroll to top in case top item was not visible
        swap(currencies, itemPosition, 0)
        recyclerView.smoothScrollToPosition(0)

        notifyDataSetChanged()
    }

    /*********************************
     * Override functions
     *********************************/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = (holder as VHCurrencyConverterItem)
//        viewHolder.bind(currencies[position])
        viewHolder.bind(currencies[position]) { onItemClicked(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = DataBindingUtil.inflate<CurrencyItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.currency_item, parent, false)

        return VHCurrencyConverterItem(binding, viewModel, lifecycleOwner)
    }

    override fun getItemCount() = currencies.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    /**************************************************
     * Private functions
     **************************************************/

    private fun onItemClicked(itemPosition: Int) {

        // do nothing if item is already at top
        if (itemPosition == 0) return

        // move item to top and scroll to top in case top item was not visible
        swap(currencies, itemPosition, 0)
        recyclerView.smoothScrollToPosition(0)

        notifyDataSetChanged()
    }
}