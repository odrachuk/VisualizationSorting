package com.softsandr.sortvisual.logger

import com.softsandr.sortvisual.di.ApplicationScope
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
class LoggerModule {

    private val loggers: ExternalLoggersTree = ExternalLoggersTree()

    @Provides
    @ApplicationScope
    fun provideLogTree(): Timber.Tree {
        // todo: in case of "if (!BuildConfig.DEBUG)" the method returns another external logging tree
        return Timber.DebugTree()
    }
}