package com.example.trubbi.holders

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R

class EventHolder (v:View) : RecyclerView.ViewHolder(v){
    private var view: View

    init{
        this.view = v
    }

    fun setName(name: String){
        val txt: TextView = view.findViewById(R.id.event_text)
        txt.text = name
    }
    fun setDate(date: String){
    }

    fun setTime(time: String){
    }

    fun setDetail(detail: String){
    }

    fun setAddress(address: String){
        val addresstxt: TextView = view.findViewById(R.id.address_text)
        addresstxt.text = "Florida 2010, 1636, Olivos, Provincia de Buenos Aires"
    }

    fun getCardLayout():CardView{
        return view.findViewById(R.id.card_view)
    }

    fun getImageView() : ImageView{
        return view.findViewById(R.id.img_item)
    }

    fun getButton() : ImageButton {
        return view.findViewById(R.id.button_card)
    }

}