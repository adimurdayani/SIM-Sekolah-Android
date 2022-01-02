package com.example.simsekolah.ui.jadwal

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Kelas
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.jadwal.adapter.AdapterDetailSiswa
import com.example.simsekolah.ui.jadwal.adapter.AdapterJadwal
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSiswa : AppCompatActivity() {
    lateinit var sw_data: SwipeRefreshLayout
    lateinit var rc_data: RecyclerView
    lateinit var btn_kembali: ImageView
    lateinit var pesan_teks: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_siswa)
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
        pesan_teks = findViewById(R.id.pesan_teks)
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

        val kelas_id = intent.getIntExtra("kelas_id", 0)
        ApiConfig.instanceRetrofit.kelas_id(kelas_id).enqueue(object :
            Callback<ResponsModel> {
            override fun onResponse(
                call: Call<ResponsModel>,
                response: Response<ResponsModel>,
            ) {
                val res = response.body()!!
                if (res.success == 1) {
                    alertDialog.dismiss()
                    sw_data.isRefreshing = false
                    setDisplay(res.siswas)
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

    private fun setDisplay(arryKelas: ArrayList<Siswa>) {

        if (arryKelas != null) {
            pesan_teks.visibility = View.GONE
            rc_data.visibility = View.VISIBLE
        } else {
            rc_data.visibility = View.GONE
            pesan_teks.visibility = View.VISIBLE
        }

        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        rc_data.adapter = AdapterDetailSiswa(arryKelas, this)
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