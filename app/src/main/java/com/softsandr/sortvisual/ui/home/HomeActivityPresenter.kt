package com.softsandr.sortvisual.ui.home

import com.softsandr.sortvisual.ui.common.ActivityPresenter
import com.softsandr.sortvisual.ui.util.QuickSort
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class HomeActivityPresenter(private var view: HomeActivityPresenter.View?) : ActivityPresenter {

    private var subscriptions = CompositeSubscription()

    interface View {
        fun onSortStarted(size: Int)
        fun onSortFinished(digits: IntArray)
    }

    override fun onPause() {
        unSubscribe()
    }

    override fun onResume() {
        // ignored for now
    }

    override fun onDestroy() {
        view = null
    }

    private fun unSubscribe() {
        subscriptions.unsubscribe()
        subscriptions = CompositeSubscription()
    }

    private fun addSubscription(subscription: Subscription?) = subscription?.let { subscriptions.add(it) }

    fun startSort(inputArray: Array<String>) {
        view?.onSortStarted(inputArray.size)
        unSubscribe()
        addSubscription(Observable.just(inputArray.map(String::toInt).toIntArray())
                .map { it -> QuickSort().sort(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe { view?.onSortFinished(it) })
    }
}