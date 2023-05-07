package com.example.libgl.utils

import android.content.res.Resources
import android.opengl.GLES30

object ShaderUtils {
    /**
     * 加载着色器
     */
    private fun loadShaper(type: Int, shaperCode: String): Int {
        val shaper = GLES30.glCreateShader(type)
        GLES30.glShaderSource(shaper, shaperCode)
        GLES30.glCompileShader(shaper)
        return shaper
    }

    /**
     * 创建program
     */
    fun createProgram(vectorCode:String, fragmentCode:String):Int{
        val vectorShaper = loadShaper(GLES30.GL_VERTEX_SHADER, vectorCode)
        val fragmentShaper = loadShaper(GLES30.GL_FRAGMENT_SHADER, fragmentCode)
        //创建一个空的OpenGLES程序
        val program = GLES30.glCreateProgram()
        //将顶点着色器加入到程序
        GLES30.glAttachShader(program, vectorShaper)
        //将片元着色器加入到程序
        GLES30.glAttachShader(program, fragmentShaper)
        //链接到程序
        GLES30.glLinkProgram(program)
        return program
    }

    fun createProgram(res: Resources, vertexRes: String?, fragmentRes: String?): Int {
        return createProgram(
            loadFromAssetsFile(vertexRes, res)!!,
            loadFromAssetsFile(fragmentRes, res)!!
        )
    }

    private fun loadFromAssetsFile(fname: String?, res: Resources): String? {
        val result = StringBuilder()
        try {
            val mInputStream = res.assets.open(fname!!)
            var ch: Int
            val buffer = ByteArray(1024)
            while (-1 != mInputStream.read(buffer).also { ch = it }) {
                result.append(String(buffer, 0, ch))
            }
        } catch (e: Exception) {
            return null
        }
        return result.toString().replace("\\r\\n".toRegex(), "\n")
    }
}