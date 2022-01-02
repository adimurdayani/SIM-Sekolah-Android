package com.example.simsekolah.ui.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simsekolah.LoginActivity
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.absen.AbsensiActivity
import com.example.simsekolah.ui.aplikasi.AplikasiActivity
import com.example.simsekolah.ui.guru.GuruActivity
import com.example.simsekolah.ui.home.adapter.AdapterSliderView
import com.example.simsekolah.ui.jadwal.JadwalActivity
import com.example.simsekolah.ui.nilai.NilaiActivity
import com.example.simsekolah.ui.profile.ProfileActivity
import com.example.simsekolah.ui.siswa.SiswaActivity
import com.example.simsekolah.ui.tentang.TentangActivity
import com.example.simsekolah.util.SharedPref
import com.example.simsekolah.util.Util
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSiswa : AppCompatActivity() {
    lateinit var imageSlider: SliderView
    lateinit var btn_profile: ImageView
    lateinit var btn_jadwal: ImageView
    lateinit var btn_dataguru: ImageView
    lateinit var btn_datasiswa: ImageView
    lateinit var btn_absensi: ImageView
    lateinit var btn_nilai: ImageView
    lateinit var btn_tentang_sekolah: ImageView
    lateinit var btn_tentang_aplikasi: ImageView
    lateinit var img_user: ImageView
    lateinit var nama: TextView
    lateinit var email: TextView
    lateinit var btn_logout: LinearLayout
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_siswa)
        s = SharedPref(this)
        setInit()
        setButton()
        setImage()
        setDisplay()
        setSiswa()
    }

    private fun setDisplay() {
        val user = s.getUser()!!
        nama.text = user.name
        email.text = user.email
    }

    private fun setButton() {
        btn_logout.setOnClickListener {
            logout()
        }

        btn_profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        btn_jadwal.setOnClickListener {
            startActivity(Intent(this, JadwalActivity::class.java))
        }
        btn_datasiswa.setOnClickListener {
            startActivity(Intent(this, SiswaActivity::class.java))
        }
        btn_nilai.setOnClickListener {
            startActivity(Intent(this, NilaiActivity::class.java))
        }
        btn_tentang_sekolah.setOnClickListener {
            startActivity(Intent(this, TentangActivity::class.java))
        }
        btn_tentang_aplikasi.setOnClickListener {
            startActivity(Intent(this, AplikasiActivity::class.java))
        }
    }

    private fun setSiswa() {
        ApiConfig.instanceRetrofit.no_induk(s.getUser()!!.no_induk).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    Log.d("Response", "Image: " + res.siswa.foto)
                    Picasso.get()
                        .load(Util.baseUrl + res.siswa.foto)
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


    private fun logout() {
        val alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_WARNING)
                .setTitle("Apakah anda yakin?")
                .setDescription("Ingin logout dari aplikasi!")
                .setPositiveText("Iya")
                .setPositiveTextColor(Color.parseColor("#ffeaea"))
                .setPositiveButtonColor(Color.parseColor("#f44242"))
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        s.setStatusLogin(false)
                        val intent = Intent(this@HomeSiswa, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                        dialog.dismiss()
                    }
                })
                .setNegativeText("Tidak")
                .setNegativeTextColor(Color.parseColor("#ffeaea"))
                .setNegativeButtonColor(Color.parseColor("#EDEDED"))
                .setNegativeListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }
                })
                .build()
        alertDialog.show()
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

    private fun setInit() {
        imageSlider = findViewById(R.id.imageSlider)
        btn_profile = findViewById(R.id.btn_profile)
        btn_jadwal = findViewById(R.id.btn_jadwal)
        btn_dataguru = findViewById(R.id.btn_dataguru)
        btn_datasiswa = findViewById(R.id.btn_datasiswa)
        btn_absensi = findViewById(R.id.btn_absensi)
        btn_nilai = findViewById(R.id.btn_nilai)
        btn_tentang_sekolah = findViewById(R.id.btn_tentang_sekolah)
        btn_tentang_aplikasi = findViewById(R.id.btn_tentang_aplikasi)
        img_user = findViewById(R.id.img_user)
        nama = findViewById(R.id.nama)
        email = findViewById(R.id.email)
        btn_logout = findViewById(R.id.btn_logout)
    }

    private fun setImage() {
        val imageList: ArrayList<Int> = ArrayList()
        imageList.add(R.drawable.slide1)
        imageList.add(R.drawable.slide2)
        imageList.add(R.drawable.slide3)
        setImageInSlider(imageList, imageSlider)
    }

    private fun setImageInSlider(images: ArrayList<Int>, imageSlider: SliderView) {
        val adapter = AdapterSliderView()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }
}