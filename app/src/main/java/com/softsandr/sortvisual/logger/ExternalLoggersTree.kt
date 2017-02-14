package com.softsandr.sortvisual.logger

import java.util.HashSet

import timber.log.Timber

internal class ExternalLoggersTree : Timber.DebugTree() {

    private val externalLoggers = HashSet<ExternalLogger>()

    fun registerLogger(externalLogger: ExternalLogger): ExternalLoggersTree {
        externalLoggers.add(externalLogger)
        return this
    }

    override fun log(priority: Int, tag: String, message: String, throwable: Throwable?) {
        for (logger in externalLoggers) {
            logger.log(priority, tag, message, throwable)
        }
    }
}