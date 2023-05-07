package com.andryu.opengl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.andryu.opengl.databinding.ActivityMainBinding
import com.example.gl2.Gles3HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLesson01.setOnClickListener {
            startActivity(Intent(this, Gles3HomeActivity::class.java))
        }
    }
}