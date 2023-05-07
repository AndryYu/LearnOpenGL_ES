package com.example.gl2.fgl

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.AppCompatSpinner
import com.example.gl2.R
import com.example.libgl.render.ColoredTriangle
import com.example.libgl.render.RegularTriangle
import com.example.libgl.render.RenderInterface
import com.example.libgl.render.Triangle

class FGLViewActivity : AppCompatActivity() {
    private var mGLSurfaceView: GLSurfaceView? = null
    private var mSpinner: AppCompatSpinner? = null
    private var mRenders: ArrayList<RenderBean>? = null
    private var mFGLRender: FGLRender? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fglview)

        mGLSurfaceView = findViewById(R.id.fgl_surfaceView)
        mSpinner = findViewById(R.id.spinner)
        init()
        initSpinner()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init(){
        mGLSurfaceView?.setEGLContextClientVersion(3)
        mFGLRender = FGLRender(this)
        mGLSurfaceView?.setRenderer(mFGLRender)
    }

    private fun initSpinner(){
        mRenders = ArrayList()
        add("三角形", Triangle::class.java)
        add("彩色三角形",ColoredTriangle::class.java)
        add("正三角形",RegularTriangle::class.java)

        mSpinner?.adapter = SpinnerAdapter()
        mSpinner?.onItemSelectedListener = object:OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mRenders!![p2].render?.let {
                    mGLSurfaceView?.onPause()
                    mFGLRender?.setShape(it)
                    mGLSurfaceView?.onResume()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun add(name: String, clazz:Class<out RenderInterface>) {
        val bean = RenderBean()
        bean.name = name
        bean.render = clazz
        mRenders!!.add(bean)
    }

    private inner class RenderBean {
        var name: String? = null
        var render: Class<out RenderInterface> ? = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class SpinnerAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return mRenders!!.size
        }

        override fun getItem(p0: Int): RenderBean {
            return mRenders!![p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(p0: Int, view: View?, p2: ViewGroup?): View {
            val mView = layoutInflater.inflate(R.layout.item_fgl_spinner, null)
            mView.apply {
                mView.findViewById<TextView>(R.id.tv_render).text = mRenders!![p0].name
            }
            return mView!!
        }
    }
}

