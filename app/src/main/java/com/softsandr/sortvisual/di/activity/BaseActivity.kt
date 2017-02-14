package com.softsandr.sortvisual.di.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.softsandr.sortvisual.App
import com.softsandr.sortvisual.ui.controller.PreferenceController
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var prefController: PreferenceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectMembers(App.getActivityComponentBuilderHolder(this))
    }

    protected abstract fun injectMembers(activityComponentBuilderHolder: ActivityComponentBuilderHolder)
}