package com.example.trubbi.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.data.EventResponse
import com.example.trubbi.holders.EventHolder
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.services.ServiceBuilder
import com.squareup.picasso.Picasso

class EventListAdapter(
    private var eventList: MutableList<EventCard>
) : RecyclerView.Adapter<EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return (EventHolder(view))
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.setName(eventList[position].name)
        holder.setDate(eventList[position].date)
        holder.setChip(eventList[position].chip)
        holder.setAddress(eventList[position].address)
        Picasso.get().load(eventList[position].urlImage).into(holder.getImageView())
        val navOptions = NavOptions.Builder().setEnterAnim(R.anim.anim_test_left).build()
        val eventId : Long = eventList[position].id
        val bundle : Bundle = bundleOf("eventId" to eventId)
        holder.itemView.setOnClickListener{
            holder.itemView.findNavController().navigate(R.id.detailsFragment,bundle,navOptions)
        }
    }


    override fun getItemCount(): Int {
        return eventList.size
    }

}