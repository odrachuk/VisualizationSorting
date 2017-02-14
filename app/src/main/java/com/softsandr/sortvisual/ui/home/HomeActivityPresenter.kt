package com.softsandr.sortvisual.ui.home

import com.softsandr.sortvisual.ui.util.QuickSort
import timber.log.Timber

class HomeActivityPresenter(private val view: HomeActivityPresenter.View) {

    interface View {
        fun onSortFinished(digits: IntArray)
    }

    fun startSort(inputArray: Array<String>) {
        Timber.d("Before:")
        val digits: IntArray = inputArray.map(String::toInt).toIntArray()
        view.onSortFinished(digits)
        Timber.d("After:")
        view.onSortFinished(QuickSort().sort(digits))
    }
}