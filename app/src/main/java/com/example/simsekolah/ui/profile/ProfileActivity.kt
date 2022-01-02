package com.example.simsekolah.ui.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.util.SharedPref
import com.example.simsekolah.util.Util
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    lateinit var nama: TextView
    lateinit var email: TextView
    lateinit var s: SharedPref
    lateinit var img_user: ImageView
    lateinit var btn_kembali:ImageView
    lateinit var btn_ubahpassword:LinearLayout
    lateinit var btn_ubahprofil:LinearLayout
    lateinit var btn_info:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        s = SharedPref(this)
        setinit()
        setDisplay()
        setGuru()
        setButton()
    }

    private fun setButton() {
        btn_kembali.setOnClickListener {
            onBackPressed()
        }
        btn_ubahpassword.setOnClickListener {
            startActivity(Intent(this, UbahPassword::class.java))
        }
        btn_ubahprofil.setOnClickListener {
            startActivity(Intent(this, UbahProfile::class.java))
        }
        btn_info.setOnClickListener {
            startActivity(Intent(this, InfoProfile::class.java))
        }
    }

    private fun setDisplay() {
        var user = s.getUser()!!
        nama.text = user.name
        email.text = user.email
    }

    private fun setinit() {
        nama = findViewById(R.id.nama)
        email = findViewById(R.id.email)
        img_user = findViewById(R.id.img_user)
        img_user = findViewById(R.id.img_user)
        btn_kembali = findViewById(R.id.btn_kembali)
        btn_ubahpassword = findViewById(R.id.btn_ubahpassword)
        btn_ubahprofil = findViewById(R.id.btn_ubahprofil)
        btn_info = findViewById(R.id.btn_info)
    }

    private fun setGuru() {
        ApiConfig.instanceRetrofit.id_card(s.getUser()!!.id_card).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    Log.d("Response", "Image: " + res.guru.foto)
                    Picasso.get()
                        .load(Util.baseUrl + res.guru.foto)
                        .placeholder(R.drawable.ic_baseline_person_24)
                        .error(R.drawable.ic_baseline_person_24)
                        .into(img_user)
                } else {
                    setError(res.message)
                }
            }

            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                setError("Terjadi kesalahan koneksi!")
                Log.d("Response", "Error: " + t.message)
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
}