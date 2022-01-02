package com.example.simsekolah.ui.nilai.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Kelas
import com.example.simsekolah.ui.jadwal.DetailJadwal
import com.example.simsekolah.ui.jadwal.DetailSiswa
import com.example.simsekolah.ui.nilai.NilaiSiswa
import com.example.simsekolah.ui.siswa.DataSiswa

class AdapterNilaiKelas(var data: ArrayList<Kelas>, var activity: Activity) :
    RecyclerView.Adapter<AdapterNilaiKelas.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        val btn_siswa = view.findViewById<LinearLayout>(R.id.btn_siswa)
        val nama_kelas = view.findViewById<TextView>(R.id.nama_kelas)
        val nama_walikelas = view.findViewById<TextView>(R.id.nama_walikelas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_kelas2, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val kelas = data[position]

        holder.nama_kelas.text = kelas.nama_kelas
        holder.nama_walikelas.text = kelas.guru.nama_guru

        holder.btn_siswa.setOnClickListener {
            val intent = Intent(activity, NilaiSiswa::class.java)
            intent.putExtra("kelas_id", kelas.id)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}