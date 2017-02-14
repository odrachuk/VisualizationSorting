package com.softsandr.sortvisual.di.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.softsandr.sortvisual.App
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var uiVisibilityController: UiVisibilityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectMembers(App.getActivityComponentBuilderHolder(this))
    }

    protected abstract fun injectMembers(activityComponentBuilderHolder: ActivityComponentBuilderHolder)

    override fun onResume() {
        super.onResume()
        uiVisibilityController.incrementUiCounter()
    }

    override fun onPause() {
        super.onPause()
        uiVisibilityController.decrementUiCounter()
    }

    override fun onStop() {
        super.onStop()
    }
}