package com.softsandr.sortvisual

import android.content.Context
import android.preference.PreferenceManager
import com.softsandr.sortvisual.di.ApplicationScope
import com.softsandr.sortvisual.logger.LoggerModule
import com.softsandr.sortvisual.ui.UiModule
import com.softsandr.sortvisual.ui.controller.PreferenceController
import dagger.Component
import dagger.Module
import dagger.Provides

@ApplicationScope
@Component(modules = arrayOf(
        AppComponent.AppModule::class, LoggerModule::class, UiModule::class
))
interface AppComponent {
    fun inject(app: App): App

    @Module
    class AppModule {
        @Provides
        @ApplicationScope
        internal fun providePreferenceController(ctx: Context): PreferenceController =
                PreferenceController(PreferenceManager.getDefaultSharedPreferences(ctx))
    }
}