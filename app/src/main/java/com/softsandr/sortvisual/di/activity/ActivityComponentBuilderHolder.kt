package com.softsandr.sortvisual.di.activity

import android.app.Activity

interface ActivityComponentBuilderHolder {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>?
}