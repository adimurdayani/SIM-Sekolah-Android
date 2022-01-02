package com.example.simsekolah.ui.auth

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.home.HomeActivity
import com.example.simsekolah.ui.home.HomeSiswa
import com.example.simsekolah.util.SharedPref
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginSiswa : AppCompatActivity() {
    lateinit var e_email: EditText
    lateinit var e_password: EditText
    lateinit var btn_login: LinearLayout
    lateinit var text_login: TextView
    lateinit var progress: ProgressBar
    lateinit var btn_register: LinearLayout
    lateinit var email: String
    lateinit var password: String
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_siswa)
        s = SharedPref(this)
        setinit()
        setButton()
    }

    private fun setButton() {
        btn_register.setOnClickListener {
            val fragment = RegisterFragment()
            val frm = supportFragmentManager.beginTransaction()
            frm.replace(R.id.frm_login, fragment)
            frm.commit()
        }
        btn_login.setOnClickListener {
            if (validasi()) {
                login()
            }
        }
    }

    private fun login() {
        email = e_email.text.toString()
        password = e_password.text.toString()

        progress.visibility = View.VISIBLE
        text_login.visibility = View.GONE
        ApiConfig.instanceRetrofit.login(email, password)
            .enqueue(object : Callback<ResponsModel> {
                override fun onResponse(
                    call: Call<ResponsModel>,
                    response: Response<ResponsModel>
                ) {
                    progress.visibility = View.GONE
                    text_login.visibility = View.VISIBLE
                    val respon = response.body()!!
                    if (respon.success == 1) {
                        s.setStatusLogin(true)
                        s.setUser(respon.data)
                        val intent = Intent(this@LoginSiswa, HomeSiswa::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        progress.visibility = View.GONE
                        text_login.visibility = View.VISIBLE
                        setError(respon.message)
                    }
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    progress.visibility = View.GONE
                    text_login.visibility = View.VISIBLE
                    setError(t.message.toString())
                }
            })
    }

    private fun setError(pesan: String) {
        var alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
                .setTitle("Something error")
                .setDescription(pesan)
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.RED)
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }

                })
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun validasi(): Boolean {
        if (e_email.text.toString().isEmpty()) {
            e_email.error = "Kolom email tidak boleh kosong!"
            e_email.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(e_email.text.toString()).matches()) {
            e_email.error = "Format email salah!. Contoh: gunakan @example.com"
            e_email.requestFocus()
            return false
        }
        if (e_password.text.toString().isEmpty()) {
            e_password.error = "Kolom password tidak boleh kosong!"
            e_password.requestFocus()
            return false
        } else if (e_password.text.toString().length < 6) {
            e_password.error = "Password tidak boleh kurang dari 6 karakter!"
            e_password.requestFocus()
            return false
        }
        return true
    }

    private fun setinit() {
        e_email = findViewById(R.id.e_email)
        e_password = findViewById(R.id.e_password)
        btn_login = findViewById(R.id.btn_login)
        text_login = findViewById(R.id.text_login)
        progress = findViewById(R.id.progress)
        btn_register = findViewById(R.id.btn_register)
    }
}