package com.softsandr.sortvisual.di.fragment

import android.support.v4.app.Fragment

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
