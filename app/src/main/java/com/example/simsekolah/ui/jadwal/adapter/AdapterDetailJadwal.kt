package com.example.simsekolah.ui.jadwal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Jadwal
import com.example.simsekolah.core.data.model.Kelas

class AdapterDetailJadwal(var data: ArrayList<Jadwal>, var listener: Listeners) :
    RecyclerView.Adapter<AdapterDetailJadwal.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.findViewById<CardView>(R.id.layout)
        val hari = view.findViewById<TextView>(R.id.hari)
        val mapel = view.findViewById<TextView>(R.id.mapel)
        val guru = view.findViewById<TextView>(R.id.guru)
        val jam_pelajaran = view.findViewById<TextView>(R.id.jam_pelajaran)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_jadwal, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val jadwal = data[position]

        holder.hari.text = jadwal.hari.nama_hari
        holder.jam_pelajaran.text = jadwal.jam_mulai + " - " + jadwal.jam_selesai
        holder.mapel.text = jadwal.mapel.nama_mapel
        holder.guru.text = jadwal.guru.nama_guru
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface Listeners {
        fun onClicked(data: Kelas)
    }
}