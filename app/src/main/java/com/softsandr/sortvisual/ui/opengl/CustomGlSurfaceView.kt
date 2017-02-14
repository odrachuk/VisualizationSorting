package com.softsandr.sortvisual.ui.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class CustomGlSurfaceView : GLSurfaceView {

    private var renderer: CustomGlRenderer? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setEGLContextClientVersion(2)
        renderer = CustomGlRenderer()
        setRenderer(renderer)
    }
}
