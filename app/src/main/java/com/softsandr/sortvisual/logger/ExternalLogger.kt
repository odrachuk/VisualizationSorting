package com.softsandr.sortvisual.logger

internal interface ExternalLogger {
    fun log(priority: Int, tag: String, message: String, throwable: Throwable?)
}