package com.softsandr.sortvisual.ui.util

import rx.Observable
import rx.Subscriber
import java.util.concurrent.TimeUnit

class QuickSort(val animationFrequency: Long?) {
    var step: Int = 0

    fun sort(array: IntArray?): Observable<IntArray> {
        if (array == null || array.isEmpty()) {
            return Observable.error(IllegalArgumentException("Input Array is not specified!"))
        }
        return Observable.create<IntArray> { subscriber -> quickSort(0, array.size - 1, array, subscriber) }
    }

    private fun quickSort(low: Int, high: Int, array: IntArray, subscriber: Subscriber<in IntArray>) {
        step++
        var i = low
        var j = high
        val pivot = array[low + (high - low) / 2]

        while (i <= j) {
            while (array[i] < pivot) {
                i++
            }
            while (array[j] > pivot) {
                j--
            }
            if (i <= j) {
                exchange(i, j, array)
                i++
                j--
                if (!subscriber.isUnsubscribed) {
                    subscriber.onNext(array.clone())
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(animationFrequency ?: ANIMATION_DURATION_MILLIS)
        } catch (e: InterruptedException) {
            if (!subscriber.isUnsubscribed) {
                subscriber.onError(e)
            }
        }

        if (low < j) {
            quickSort(low, j, array, subscriber)
        }

        if (i < high) {
            quickSort(i, high, array, subscriber)
        }
        if (--step == 0) {
            if (!subscriber.isUnsubscribed) {
                subscriber.onCompleted()
            }
        }
    }

    private fun exchange(i: Int, j: Int, array: IntArray) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    companion object {
        val ANIMATION_DURATION_MILLIS = 1000L
    }
}
