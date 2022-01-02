package com.example.simsekolah.ui.nilai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.util.Util
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailSiswa : AppCompatActivity() {
    lateinit var btn_kembali: ImageView
    lateinit var img_user: ImageView
    lateinit var nama: TextView
    lateinit var nomor_induk: TextView
    lateinit var nis: TextView
    lateinit var kelas: TextView
    lateinit var kelamin: TextView
    lateinit var tempat_lahir: TextView
    lateinit var tanggal_lahir: TextView
    lateinit var no_telepon: TextView
    lateinit var siswa: Siswa
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_siswa2)
        setinit()
        setbutton()
        setDisplay()
    }

    private fun setDisplay() {
        val data = intent.getStringExtra("extra")
        siswa = Gson().fromJson<Siswa>(data, Siswa::class.java)

        nama.text = siswa.nama_siswa
        nomor_induk.text = siswa.no_induk
        nis.text = siswa.nis
        kelas.text = siswa.kelas.nama_kelas
        kelamin.text = siswa.jk
        tempat_lahir.text = siswa.tmp_lahir
        tanggal_lahir.text = siswa.tgl_lahir
        no_telepon.text = siswa.telp

        Picasso.get()
            .load(Util.baseUrl + siswa.foto)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(img_user)
    }

    private fun setinit() {
        btn_kembali = findViewById(R.id.btn_kembali)
        img_user = findViewById(R.id.img_user)
        nama = findViewById(R.id.nama)
        nomor_induk = findViewById(R.id.nomor_induk)
        nis = findViewById(R.id.nis)
        kelas = findViewById(R.id.kelas)
        kelamin = findViewById(R.id.kelamin)
        tempat_lahir = findViewById(R.id.tempat_lahir)
        tanggal_lahir = findViewById(R.id.tanggal_lahir)
        no_telepon = findViewById(R.id.no_telepon)
    }

    private fun setbutton() {
        btn_kembali.setOnClickListener { onBackPressed() }
    }
}