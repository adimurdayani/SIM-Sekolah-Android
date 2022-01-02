package com.example.simsekolah.ui.nilai

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Jadwal
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.home.HomeActivity
import com.google.gson.Gson
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiUlangan : AppCompatActivity() {
    lateinit var btn_kembali: ImageView
    lateinit var btn_home: ImageView
    lateinit var nomor_induk: TextView
    lateinit var nama_siswa: TextView
    lateinit var nama_kelas: TextView
    lateinit var wali_kelas: TextView
    lateinit var ulha_1: TextView
    lateinit var ulha_2: TextView
    lateinit var uts: TextView
    lateinit var ulha_3: TextView
    lateinit var uas: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nilai_ulangan)
        setinit()
        setButton()
    }

    private fun setData() {
        val alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()

        val mapel_id = intent.getIntExtra("mapel_id", 0)

        ApiConfig.instanceRetrofit.ulangan(mapel_id).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    alertDialog.dismiss()
                    if (res.ulangan == null) {
                        ulha_1.visibility = View.GONE
                        ulha_2.visibility = View.GONE
                        ulha_3.visibility = View.GONE
                        uts.visibility = View.GONE
                        uas.visibility = View.GONE
                    } else {
                        ulha_1.text = res.ulangan.ulha_1
                        ulha_2.text = res.ulangan.ulha_2
                        uts.text = res.ulangan.uts
                        ulha_3.text = res.ulangan.ulha_3
                        uas.text = res.ulangan.uas
                    }
                } else {
                    alertDialog.dismiss()
                    setError(res.message)
                }
            }

            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                setError("Terjadi kesalahan koneksi!")
                alertDialog.dismiss()
                Log.d("Response", "Error: " + t.message)
            }
        })
    }

    private fun setDataSiswa() {
        val alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()

        val kelas_id = intent.getIntExtra("kelas_id", 0)

        ApiConfig.instanceRetrofit.siswa_kelas_id(kelas_id).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    alertDialog.dismiss()
                    nomor_induk.text = res.data_siswa.no_induk
                    nama_siswa.text = res.data_siswa.nama_siswa
                    nama_kelas.text = res.data_siswa.kelas.nama_kelas
                    wali_kelas.text = res.data_siswa.kelas.guru.nama_guru
                    Log.d("Response", "Data: " + res.siswa.nama_siswa)
                } else {
                    alertDialog.dismiss()
                    setError(res.message)
                }
            }

            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                setError("Terjadi kesalahan koneksi!")
                alertDialog.dismiss()
                Log.d("Response", "Error: " + t.message)
            }
        })
    }

    private fun setinit() {
        btn_kembali = findViewById(R.id.btn_kembali)
        nomor_induk = findViewById(R.id.nomor_induk)
        nama_siswa = findViewById(R.id.nama_siswa)
        nama_kelas = findViewById(R.id.nama_kelas)
        wali_kelas = findViewById(R.id.wali_kelas)
        ulha_1 = findViewById(R.id.ulha_1)
        ulha_2 = findViewById(R.id.ulha_2)
        uts = findViewById(R.id.uts)
        ulha_3 = findViewById(R.id.ulha_3)
        uas = findViewById(R.id.uas)
        btn_home = findViewById(R.id.btn_home)
    }

    private fun setError(pesan: String) {
        val alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
                .setTitle("Opps")
                .setDescription(pesan)
                .setNegativeText("Oke")
                .setNegativeTextColor(Color.WHITE)
                .setNegativeListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }

                })
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun setButton() {
        btn_kembali.setOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        setData()
        setDataSiswa()
        super.onResume()
    }
}