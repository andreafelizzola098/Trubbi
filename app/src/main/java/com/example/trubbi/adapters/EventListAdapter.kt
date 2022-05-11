package com.example.trubbi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.entities.Event
import com.example.trubbi.holders.EventHolder
import com.squareup.picasso.Picasso

class EventListAdapter (
    private  var eventList: MutableList<Event>,
    val onItemClick:(Int) -> Boolean
) : RecyclerView.Adapter<EventHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return (EventHolder(view))
    }

    fun setData(newData: ArrayList<Event>) {
        this.eventList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val img = eventList[position].urlImage
        holder.setName(eventList[position].name)
        holder.setDate(eventList[position].date)
        holder.setTime(eventList[position].time)
        holder.setDetail(eventList[position].detail)
        holder.setAddress(eventList[position].address)
        holder.getButton()
        Picasso.get().load(eventList[position].urlImage).into(holder.getImageView())
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}