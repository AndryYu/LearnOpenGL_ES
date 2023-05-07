package com.example.gl2.image

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gl2.R
import com.example.gl2.image.filter.ColorFilter
import com.example.gl2.image.filter.ContrastColorFilter

/**
 * Description:
 */
class SGLViewActivity : AppCompatActivity() {
    private var mGLView: SGLView? = null
    private var isHalf = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)
        mGLView = findViewById(R.id.glView)
    }

    override fun onResume() {
        super.onResume()
        mGLView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGLView!!.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_filter, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.mDeal) {
            isHalf = !isHalf
            if (isHalf) {
                item.title = "处理一半"
            } else {
                item.title = "全部处理"
            }
            mGLView!!.render?.refresh()
        } else if (itemId == R.id.mDefault) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.NONE))
        } else if (itemId == R.id.mGray) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.GRAY))
        } else if (itemId == R.id.mCool) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.COOL))
        } else if (itemId == R.id.mWarm) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.WARM))
        } else if (itemId == R.id.mBlur) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.BLUR))
        } else if (itemId == R.id.mMagn) {
            mGLView!!.setFilter(ContrastColorFilter(this, ColorFilter.Filter.MAGN))
        }
        mGLView!!.render?.filter?.setHalf(isHalf)
        mGLView!!.requestRender()
        return super.onOptionsItemSelected(item)
    }
}