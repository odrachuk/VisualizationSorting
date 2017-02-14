package com.softsandr.sortvisual.di.activity

import com.softsandr.sortvisual.di.ActivityScope

import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule<T>(protected val activity: T) {

    @Provides
    @ActivityScope
    fun provideActivity(): T = activity
}
