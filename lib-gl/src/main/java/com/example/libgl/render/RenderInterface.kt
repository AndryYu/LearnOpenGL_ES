package com.example.libgl.render

interface RenderInterface {

    fun onCreated()

    fun onChanged(width: Int, height: Int)

    fun onDrawFrame()
}