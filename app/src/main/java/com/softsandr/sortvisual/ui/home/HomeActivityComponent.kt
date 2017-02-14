package com.softsandr.sortvisual.ui.home

import com.softsandr.sortvisual.di.ActivityScope
import com.softsandr.sortvisual.di.activity.ActivityComponent
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilder
import com.softsandr.sortvisual.di.activity.ActivityModule
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        HomeActivityComponent.HomeActivityModule::class
))
interface HomeActivityComponent : ActivityComponent<HomeActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<HomeActivityModule, HomeActivityComponent>

    @Module
    class HomeActivityModule internal constructor(activity: HomeActivity) : ActivityModule<HomeActivity>(activity) {

        @Provides
        @ActivityScope
        internal fun providePresenter(): HomeActivityPresenter = HomeActivityPresenter(activity)
    }
}