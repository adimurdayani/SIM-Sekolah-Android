package com.example.simsekolah.ui.guru.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.Jadwal
import com.example.simsekolah.core.data.model.Kelas
import com.example.simsekolah.core.data.model.Mapel
import com.example.simsekolah.core.data.model.Siswa
import com.example.simsekolah.ui.guru.DetailGuruMapel
import com.example.simsekolah.util.Util
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class AdapterMapel(var data: ArrayList<Mapel>, var activity: Activity) :
    RecyclerView.Adapter<AdapterMapel.HolderData>() {
    class HolderData(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.findViewById<CardView>(R.id.layout)
        val nama_mapel = view.findViewById<TextView>(R.id.nama_mapel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_mapel, parent, false)
        return HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val mapel = data[position]

        holder.nama_mapel.text = mapel.nama_mapel

        holder.layout.setOnClickListener {
            val intent = Intent(activity, DetailGuruMapel::class.java)
            intent.putExtra("mapel_id", mapel.id)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}