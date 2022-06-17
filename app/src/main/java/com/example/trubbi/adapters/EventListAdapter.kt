package com.example.trubbi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.data.EventResponse
import com.example.trubbi.holders.EventHolder
import com.example.trubbi.interfaces.APIService
import com.example.trubbi.model.EventCard
import com.example.trubbi.services.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

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
        holder.itemView.setOnClickListener{

            holder.itemView.findNavController().navigate(R.id.detailsFragment,null,navOptions)
            getEventById(1, holder)
        }
    }

    private fun getEventById(id: Number, holder: EventHolder){
        val apiService: APIService = ServiceBuilder.buildService(APIService::class.java)
        val requestCall: Call<EventResponse> = apiService.getEventById(id)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                       // holder.itemView.findViewById<TextView>(R.id.textView3)
                    }
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}