package com.softsandr.sortvisual.di.fragment

import com.softsandr.sortvisual.di.FragmentScope

import dagger.Module
import dagger.Provides

@Module
abstract class FragmentModule<T>(protected val fragment: T) {

    @Provides
    @FragmentScope
    fun provideFragment(): T {
        return fragment
    }
}
