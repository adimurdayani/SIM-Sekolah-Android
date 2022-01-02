package com.example.simsekolah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.simsekolah.ui.auth.LoginFragment

class LoginActivity : AppCompatActivity() {
    val fragmentLogin: Fragment = LoginFragment()
    val fm: FragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fm.beginTransaction().add(R.id.frm_login, fragmentLogin).show(fragmentLogin).commit()
    }
}