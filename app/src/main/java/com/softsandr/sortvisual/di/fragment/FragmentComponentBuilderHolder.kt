package com.softsandr.sortvisual.di.fragment

import android.support.v4.app.Fragment

interface FragmentComponentBuilderHolder {
    fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *>?
}