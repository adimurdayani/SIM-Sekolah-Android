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
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.ui.nilai.DetailNIlai
import com.example.simsekolah.ui.nilai.DetailSiswa
import com.example.simsekolah.util.Util
import com.google.gson.Gson
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class AdapterNilaiSiswa(var data: ArrayList<Siswa>, var activity: Activity) :
    RecyclerView.Adapter<AdapterNilaiSiswa.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.findViewById<CardView>(R.id.layout)
        val img_siswa = view.findViewById<CircularImageView>(R.id.img_siswa)
        val nama_siswa = view.findViewById<TextView>(R.id.nama_siswa)
        val nomor_induk = view.findViewById<TextView>(R.id.nomor_induk)
        val btn_siswa = view.findViewById<LinearLayout>(R.id.btn_siswa)
        val btn_nilai = view.findViewById<LinearLayout>(R.id.btn_nilai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_siswa2, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val siswa = data[position]

        holder.nama_siswa.text = siswa.nama_siswa
        holder.nomor_induk.text = siswa.no_induk

        Picasso.get()
            .load(Util.baseUrl + siswa.foto)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.img_siswa)

        holder.btn_nilai.setOnClickListener {
            val intent = Intent(activity, DetailNIlai::class.java)
            val str = Gson().toJson(data[position], Siswa::class.java)
            intent.putExtra("extra", str)
            activity.startActivity(intent)
        }

        holder.btn_siswa.setOnClickListener {
            val intent = Intent(activity, DetailSiswa::class.java)
            val str = Gson().toJson(data[position], Siswa::class.java)
            intent.putExtra("extra", str)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}