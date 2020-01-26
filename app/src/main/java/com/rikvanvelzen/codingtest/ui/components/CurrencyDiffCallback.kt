package com.rikvanvelzen.codingtest.ui.components

import androidx.recyclerview.widget.DiffUtil
import com.rikvanvelzen.codingtest.data.models.domain.Currency

class CurrencyDiffCallback(var newCurrency: List<Currency>, var oldCurrency: List<Currency>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldCurrency.size
    }

    override fun getNewListSize(): Int {
        return newCurrency.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrency[oldItemPosition].abbreviation === newCurrency[newItemPosition].abbreviation
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrency[oldItemPosition] == newCurrency[newItemPosition]
    }
}