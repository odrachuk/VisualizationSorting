package com.softsandr.sortvisual.di.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is FragmentComponentBuilderHolder) {
            injectMembers(activity as FragmentComponentBuilderHolder)
        }
    }

    protected abstract fun injectMembers(fragmentComponentBuilderHolder: FragmentComponentBuilderHolder)
}