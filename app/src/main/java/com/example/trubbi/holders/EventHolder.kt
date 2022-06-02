package com.example.trubbi.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.google.android.material.chip.Chip

class EventHolder (v:View) : RecyclerView.ViewHolder(v){
    private var view: View = v

    fun setName(name: String){
        val txt: TextView = view.findViewById(R.id.event_text)
        txt.text = name
    }
    fun setDate(date: String){
        val txt: TextView = view.findViewById(R.id.event_date)
        txt.text = date
    }

    fun setChip(chip: String){
        val txt: Chip = view.findViewById(R.id.chip_text)
        txt.text = chip
    }

    fun setAddress(address: String){
        val txt: TextView = view.findViewById(R.id.event_address)
        txt.text = address
    }

    fun getImageView() : ImageView{
        return view.findViewById(R.id.img_item)
    }
}