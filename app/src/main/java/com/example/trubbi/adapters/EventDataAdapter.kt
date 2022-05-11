package com.example.trubbi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.entities.EventData
import com.example.trubbi.holders.EventDataViewHolder

class EventDataAdapter(val eventDataList: List<EventData>) :
    RecyclerView.Adapter<EventDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventDataViewHolder(layoutInflater.inflate(R.layout.event_item, parent, false))
    }

    override fun onBindViewHolder(holder: EventDataViewHolder, position: Int) {
        val item = eventDataList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return eventDataList.size
    }
}