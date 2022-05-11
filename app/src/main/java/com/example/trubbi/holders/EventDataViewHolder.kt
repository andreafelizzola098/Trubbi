package com.example.trubbi.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.entities.Event
import com.example.trubbi.entities.EventData

class EventDataViewHolder(view:View):RecyclerView.ViewHolder (view){

    val eventImg = view.findViewById<ImageView>(R.id.img_item)
    val eventName = view.findViewById<TextView>(R.id.event_text)
    val eventDate = view.findViewById<TextView>(R.id.date_text)
    val eventTime = view.findViewById<TextView>(R.id.time_text)
    val eventDetail = view.findViewById<TextView>(R.id.detail_text)
    val eventAddress = view.findViewById<TextView>(R.id.address_text)

    fun render(event:EventData){
        eventName.text = event.name
        eventDate.text = event.date
        eventTime.text = event.time
        eventDetail.text = event.detail
        eventAddress.text = event.address

    }
}