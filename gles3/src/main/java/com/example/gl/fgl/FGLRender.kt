package com.example.gl.fgl

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.libgl.render.RenderInterface
import com.example.libgl.render.Triangle
import java.lang.reflect.Constructor
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Description:
 */
class FGLRender(context: Context) : GLSurfaceView.Renderer {
    private var shape: RenderInterface? = null
    private var clazz: Class<out RenderInterface> = Triangle::class.java
    private val mContext:Context

    init {
        mContext = context
    }

    fun setShape(shape: Class<out RenderInterface>) {
        clazz = shape
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        try {
            val constructor: Constructor<*> = clazz.getDeclaredConstructor(
                Context::class.java
            )
            constructor.isAccessible = true
            shape = constructor.newInstance(mContext) as RenderInterface
        } catch (e: Exception) {
            e.printStackTrace()
            shape = Triangle(mContext)
        }
        shape?.onCreated()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        shape?.onChanged( width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        shape?.onDrawFrame()
    }
}