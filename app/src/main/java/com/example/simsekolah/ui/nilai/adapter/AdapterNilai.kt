package com.example.simsekolah.ui.nilai.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.*
import com.example.simsekolah.ui.nilai.NilaiUlangan
import com.google.gson.Gson

class AdapterNilai(var data: ArrayList<Jadwal>, var activity: Activity) :
    RecyclerView.Adapter<AdapterNilai.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.findViewById<CardView>(R.id.layout)
        val nama_mapel = view.findViewById<TextView>(R.id.nama_mapel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_mapel2, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val nilai_jadwal = data[position]
        holder.nama_mapel.text = nilai_jadwal.mapel.nama_mapel
        holder.layout.setOnClickListener {
            val intent = Intent(activity, NilaiUlangan::class.java)
            intent.putExtra("mapel_id", nilai_jadwal.mapel_id)
            intent.putExtra("kelas_id", nilai_jadwal.kelas_id)
            Log.d("Respoonse", "mapel id: "+nilai_jadwal.mapel_id)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}