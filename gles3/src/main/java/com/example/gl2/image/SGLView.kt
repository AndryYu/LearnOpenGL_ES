package com.example.gl2.image

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.example.gl2.image.filter.AFilter
import java.io.IOException

/**
 * Description:
 */
class SGLView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    GLSurfaceView(context, attrs) {
    var render: SGLRender? = null
        private set

    init {
        init()
    }

    private fun init() {
        setEGLContextClientVersion(2)
        render = SGLRender(this)
        setRenderer(render)
        renderMode = RENDERMODE_WHEN_DIRTY
        try {
            render!!.setImage(BitmapFactory.decodeStream(resources.assets.open("texture/fengj.png")))
            requestRender()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setFilter(filter: AFilter?) {
        if (filter != null) {
            render!!.filter = filter
        }
    }
}