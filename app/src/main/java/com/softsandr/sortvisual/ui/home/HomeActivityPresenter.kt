package com.softsandr.sortvisual.ui.home

import com.softsandr.sortvisual.ui.common.ActivityPresenter
import com.softsandr.sortvisual.ui.util.QuickSort
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class HomeActivityPresenter(private var view: HomeActivityPresenter.View?) : ActivityPresenter {

    private var subscriptions = CompositeSubscription()

    interface View {
        fun onSortStarted(size: Int)
        fun onSortStep(digits: IntArray)
        fun onSortFinished(digits: IntArray)
        fun onSortError(th: Throwable?)
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
        val input = inputArray.map(String::toInt).toIntArray()
        addSubscription(QuickSort().sort(input)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<IntArray>() {

                    override fun onNext(t: IntArray?) {
                        t?.let { view?.onSortStep(t) }
                    }

                    override fun onError(e: Throwable?) {
                        view?.onSortError(e)
                    }

                    override fun onCompleted() {
                        view?.onSortFinished(input)
                    }
                }))
    }

    fun stopSort() {
        unSubscribe()
    }
}