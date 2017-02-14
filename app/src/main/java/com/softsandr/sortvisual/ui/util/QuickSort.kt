package com.softsandr.sortvisual.ui.util

/**
 * @author Aleksander Drachuk (adrachuk@viewster.com)
 */

class QuickSort {

    fun sort(array: IntArray): IntArray {
        if (array.isEmpty()) {
            return array
        }
        quickSort(0, array.size - 1, array)
        return array
    }

    private fun quickSort(low: Int, high: Int, array: IntArray) {
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
            }
        }
        if (low < j) {
            quickSort(low, j, array)
        }
        if (i < high) {
            quickSort(i, high, array)
        }
    }

    private fun exchange(i: Int, j: Int, array: IntArray) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }
}
