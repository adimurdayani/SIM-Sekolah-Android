package com.example.simsekolah.ui.guru.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.*
import com.example.simsekolah.util.Util
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class AdapterGuru(var data: ArrayList<Guru>, var activity: Activity) :
    RecyclerView.Adapter<AdapterGuru.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        //        val layout = view.findViewById<CardView>(R.id.layout)
        val img_guru = view.findViewById<CircularImageView>(R.id.img_guru)
        val nama_guru = view.findViewById<TextView>(R.id.nama_guru)
        val nip = view.findViewById<TextView>(R.id.nip)
        val no_idcard = view.findViewById<TextView>(R.id.no_idcard)
        val nama_mapel = view.findViewById<TextView>(R.id.nama_mapel)
        val kode_jadwal = view.findViewById<TextView>(R.id.kode_jadwal)
        val kelamin = view.findViewById<TextView>(R.id.kelamin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_guru, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val guru = data[position]

        holder.nama_mapel.text = ": " + guru.mapel.nama_mapel
        holder.nama_guru.text = ": " + guru.nama_guru
        holder.nip.text = ": " + guru.nip
        holder.no_idcard.text = ": " + guru.id_card
        holder.kode_jadwal.text = ": " + guru.kode
        holder.kelamin.text = ": " + guru.jk

        Picasso.get()
            .load(Util.baseUrl + guru.foto)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.img_guru)

//        holder.layout.setOnClickListener {
//
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}