package com.example.libgl.render

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.example.libgl.utils.ShaderUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 三角形绘制
 */
class Triangle(context: Context) : RenderInterface {
    private lateinit var vertexBuffer: FloatBuffer
    private var shaderProgram: Int = 0

    private val triangleCoords = floatArrayOf(
        0.0f, 0.5f, 0.0f,
        -0.5f, -0.3f, 0.0f,
        0.5f, -0.3f, 0.0f
    )
    private val coordsInVertex = 3
    private val vertexCount = triangleCoords.size / coordsInVertex
    private val vertexStride = coordsInVertex * 4

    var positionHandler = 0
    var colorHandler = 0

    private val color = floatArrayOf(1f, 0f, 0f, 1f)
    private val mContext: Context

    init {
        mContext = context
    }

    private fun create() {
        val byteBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        vertexBuffer = byteBuffer.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)
        shaderProgram = ShaderUtils.createProgram(
            mContext.resources,
            "vshader/triangle.sh",
            "fshader/triangle.sh"
        )
    }

    override fun onCreated() {
        GLES30.glClearColor(0f, 1f, 1f, 1f)
        create()
    }

    override fun onChanged(width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        GLES30.glUseProgram(shaderProgram)
        //获取程序中顶点位置属性引用
        positionHandler = GLES30.glGetAttribLocation(shaderProgram, "vPosition")
        //启用顶点位置
        GLES30.glEnableVertexAttribArray(positionHandler)
        //将顶点数组位置数据送入渲染管线
        GLES30.glVertexAttribPointer(
            positionHandler,
            coordsInVertex,
            GLES30.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )
        //获取程序中颜色属性引用
        colorHandler = GLES30.glGetUniformLocation(shaderProgram, "vColor")
        //加载颜色统一变量
        GLES30.glUniform4fv(colorHandler, 1, color, 0)
        //绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)
        GLES30.glDisableVertexAttribArray(positionHandler)
    }

}