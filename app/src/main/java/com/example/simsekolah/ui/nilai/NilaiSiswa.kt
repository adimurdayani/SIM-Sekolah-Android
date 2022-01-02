package com.example.simsekolah.ui.nilai

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.nilai.adapter.AdapterNilaiSiswa
import com.example.simsekolah.ui.siswa.adapter.AdapterDetailSiswa
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NilaiSiswa : AppCompatActivity() {

    lateinit var sw_data: SwipeRefreshLayout
    lateinit var rc_data: RecyclerView
    lateinit var btn_kembali: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nilai_siswa)
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

        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        rc_data.adapter = AdapterNilaiSiswa(arryKelas, this)
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