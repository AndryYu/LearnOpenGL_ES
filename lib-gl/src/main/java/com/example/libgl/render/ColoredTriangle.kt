package com.example.libgl.render

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.libgl.utils.ShaderUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ColoredTriangle(context: Context) :GLSurfaceView.Renderer {
    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var colorBuffer:FloatBuffer
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

    private var color = floatArrayOf(
        0.0f, 1.0f, 0.0f, 1.0f,
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f
    )
    private val mContext: Context

    private val mViewMatrix = FloatArray(16)
    private val mProjectMatrix = FloatArray(16)
    private val mMVPMatrix = FloatArray(16)
    private var mMatrixHandler = 0

    init {
        mContext = context
    }

    private fun create() {
        val byteBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        vertexBuffer = byteBuffer.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)

        val colorByteBuffer = ByteBuffer.allocateDirect(color.size * 4)
        colorByteBuffer.order(ByteOrder.nativeOrder())

        colorBuffer = colorByteBuffer.asFloatBuffer()
        colorBuffer.put(color)
        colorBuffer.position(0)

        shaderProgram = ShaderUtils.createProgram(
            mContext.resources,
            "vshader/colortriangle.sh",
            "fshader/triangle.sh"
        )
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        GLES30.glClearColor(0f, 1f, 1f, 1f)
        GLES30.glViewport(0, 0, 1280, 960)
        create()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        //计算宽高比
        val ratio: Float = width.toFloat() / height
        //设置透视投影
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        //设置相机位置
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        //计算变换矩阵
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)


        GLES30.glUseProgram(shaderProgram)
        //获取变换矩阵vMatrix成员句柄
        //获取变换矩阵vMatrix成员句柄
        mMatrixHandler = GLES30.glGetUniformLocation(shaderProgram, "vMatrix")
        //指定vMatrix的值
        //指定vMatrix的值
        GLES30.glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0)
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
        colorHandler = GLES30.glGetUniformLocation(shaderProgram, "aColor")
        GLES30.glEnableVertexAttribArray(colorHandler)
        //将顶点数组位置数据送入渲染管线
        GLES30.glVertexAttribPointer(
            colorHandler,
            4,
            GLES30.GL_FLOAT,
            false,
            0,
            colorBuffer
        )
        //绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)
        GLES30.glDisableVertexAttribArray(positionHandler)
    }
}