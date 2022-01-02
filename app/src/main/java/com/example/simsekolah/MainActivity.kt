package com.example.simsekolah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.simsekolah.ui.home.HomeActivity
import com.example.simsekolah.ui.home.HomeSiswa
import com.example.simsekolah.ui.utama.MenuUtama
import com.example.simsekolah.util.SharedPref

class MainActivity : AppCompatActivity() {
    private val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.postDelayed(Runnable {
            startActivity(
                Intent(
                    this@MainActivity, MenuUtama::class.java
                )
            )
            finish()
        }, 3000)
    }
}