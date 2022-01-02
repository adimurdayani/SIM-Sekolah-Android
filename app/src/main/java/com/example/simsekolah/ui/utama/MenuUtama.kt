package com.example.simsekolah.ui.utama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.simsekolah.LoginActivity
import com.example.simsekolah.R
import com.example.simsekolah.ui.home.HomeActivity
import com.example.simsekolah.ui.home.HomeSiswa
import com.example.simsekolah.util.SharedPref

class MenuUtama : AppCompatActivity() {
    lateinit var btn_login_siswa: LinearLayout

    private lateinit var s: SharedPref
    lateinit var btn_login_guru: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_utama)
        s = SharedPref(this)
        setinit()
    }

    private fun setinit() {
        btn_login_siswa = findViewById(R.id.btn_login_siswa)
        btn_login_guru = findViewById(R.id.btn_login_guru)

        btn_login_siswa.setOnClickListener {
            if (s.getStatusLogin()) {
                startActivity(Intent(this, HomeSiswa::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        btn_login_guru.setOnClickListener {
            if (s.getStatusLogin()) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}