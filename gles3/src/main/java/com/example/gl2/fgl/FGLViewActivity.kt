package com.example.gl2.fgl

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatSpinner
import com.example.gl2.R
import com.example.libgl.render.ColoredTriangle
import com.example.libgl.render.Triangle

class FGLViewActivity : AppCompatActivity() {
    private var mGLSurfaceView: GLSurfaceView? = null
    var mSpinner: AppCompatSpinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fglview)

        mGLSurfaceView = findViewById(R.id.fgl_surfaceView)
        mSpinner = findViewById(R.id.spinner)
        init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init(){
        mGLSurfaceView?.setEGLContextClientVersion(3)
        mGLSurfaceView?.setRenderer(ColoredTriangle(this))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == androidx.appcompat.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}