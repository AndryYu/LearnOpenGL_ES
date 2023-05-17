package com.example.gl

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gl.Gles3HomeActivity.MenuAdapter.MenuHolder
import com.example.gl.fgl.FGLViewActivity
import com.example.gl.image.SGLViewActivity
import com.example.gl.vary.VaryActivity

class Gles3HomeActivity : AppCompatActivity(), View.OnClickListener {

    private var mList: RecyclerView? = null
    private var data: ArrayList<MenuBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl_home)
        mList = findViewById<View>(R.id.mList) as RecyclerView
        mList!!.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        data = ArrayList()
        add("绘制形体", FGLViewActivity::class.java)
        add("图片处理", SGLViewActivity::class.java)
        add("图形变换", VaryActivity::class.java)
//        add("相机",CameraActivity.class)
//        add("相机2 动画",Camera2Activity.class)
//        add("相机3 美颜",Camera3Activity.class)
//        add("压缩纹理动画",ZipActivity.class)
//        add("FBO使用",FBOActivity.class)
//        add("EGL后台处理",EGLBackEnvActivity.class)
//        add("3D obj模型",ObjLoadActivity.class)
//        add("obj+mtl模型",ObjLoadActivity2.class)
//        add("VR效果",VrContextActivity.class)
//        add("颜色混合",BlendActivity.class)
//        add("光照",LightActivity.class)
        mList!!.adapter = MenuAdapter()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun add(name: String, clazz: Class<*>) {
        val bean = MenuBean()
        bean.name = name
        bean.clazz = clazz
        data!!.add(bean)
    }

    private inner class MenuBean {
        var name: String? = null
        var clazz: Class<*>? = null
    }

    private inner class MenuAdapter : RecyclerView.Adapter<MenuHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
            return MenuHolder(layoutInflater.inflate(R.layout.item_button, parent, false))
        }

        override fun onBindViewHolder(holder: MenuHolder, position: Int) {
            holder.position = position
        }

        override fun getItemCount(): Int {
            return data!!.size
        }

        inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val mBtn: Button

            init {
                mBtn = itemView.findViewById<View>(R.id.mBtn) as Button
                mBtn.setOnClickListener(this@Gles3HomeActivity)
            }

            fun setPosition(position: Int) {
                val bean = data!![position]
                mBtn.text = bean.name
                mBtn.tag = position
            }
        }
    }

    override fun onClick(view: View) {
        val position = view.tag as Int
        val bean = data!![position]
        startActivity(Intent(this, bean.clazz))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}