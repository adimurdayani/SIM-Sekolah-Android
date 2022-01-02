package com.example.simsekolah.ui.nilai

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.*
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.nilai.adapter.AdapterNilai
import com.example.simsekolah.ui.nilai.adapter.AdapterNilaiKelas
import com.google.gson.Gson
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailNIlai : AppCompatActivity() {
    lateinit var sw_data: SwipeRefreshLayout
    lateinit var rc_data: RecyclerView
    lateinit var btn_kembali: ImageView
    lateinit var siswa: Siswa
    lateinit var nomor_induk: TextView
    lateinit var nama_siswa: TextView
    lateinit var nama_kelas: TextView
    lateinit var wali_kelas: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_n_ilai)
        setinit()
        setButton()
    }

    private fun setButton() {
        btn_kembali.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setinit() {
        sw_data = findViewById(R.id.sw_data)
        rc_data = findViewById(R.id.rc_data)
        btn_kembali = findViewById(R.id.btn_kembali)
        nomor_induk = findViewById(R.id.nomor_induk)
        nama_siswa = findViewById(R.id.nama_siswa)
        nama_kelas = findViewById(R.id.nama_kelas)
        wali_kelas = findViewById(R.id.wali_kelas)
    }

    private fun setData() {
        val alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()

        sw_data.isRefreshing = true

        val data = intent.getStringExtra("extra")
        siswa = Gson().fromJson<Siswa>(data, Siswa::class.java)
        val id = siswa.kelas_id

        ApiConfig.instanceRetrofit.nilai(id).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    alertDialog.dismiss()
                    sw_data.isRefreshing = false
                    setDisplay(res.jadwal)
                } else {
                    alertDialog.dismiss()
                    sw_data.isRefreshing = false
                    setError(res.message)
                }
            }

            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                setError("Terjadi kesalahan koneksi!")
                sw_data.isRefreshing = false
                alertDialog.dismiss()
                Log.d("Response", "Error: " + t.message)
            }
        })
    }

    private fun setDisplay(arrJadwal: ArrayList<Jadwal>) {
        val data = intent.getStringExtra("extra")
        siswa = Gson().fromJson<Siswa>(data, Siswa::class.java)

        nomor_induk.text = siswa.no_induk
        nama_siswa.text = siswa.nama_siswa
        nama_kelas.text = siswa.kelas.nama_kelas
        wali_kelas.text = siswa.kelas.guru.nama_guru

        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        rc_data.adapter = AdapterNilai(arrJadwal, this)
        rc_data.layoutManager = layout

        sw_data.setOnRefreshListener { setData() }
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

    override fun onResume() {
        setData()
        super.onResume()
    }
}