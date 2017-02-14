package com.softsandr.sortvisual.ui.home

class HomeActivityPresenter(private val view: HomeActivityPresenter.View) {

    interface View {
        fun onSortFinished()
    }

    fun startSort() {
        view.onSortFinished()
    }
}
