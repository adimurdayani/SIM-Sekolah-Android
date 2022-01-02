package com.example.simsekolah.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.simsekolah.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class AdapterSliderView : SliderViewAdapter<AdapterSliderView.HolderData>() {
    private var mSliderItems = ArrayList<Int>()

    fun renewItems(sliderItems: ArrayList<Int>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Int) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    class HolderData(itemvew: View) : SliderViewAdapter.ViewHolder(itemvew) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): HolderData {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_carouser_banner, null)
        return HolderData(inflate)
    }

    override fun onBindViewHolder(viewHolder: HolderData, position: Int) {
        Picasso.get().load(mSliderItems[position]).fit().into(viewHolder.imageView)
    }
}