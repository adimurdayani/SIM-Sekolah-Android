package com.example.simsekolah.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.simsekolah.R

class UbahPassword : AppCompatActivity() {
    lateinit var btn_kembali:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_password)
        setinit()
    }

    private fun setinit() {
        btn_kembali = findViewById(R.id.btn_kembali)
        btn_kembali.setOnClickListener {
            onBackPressed()
        }
    }
}