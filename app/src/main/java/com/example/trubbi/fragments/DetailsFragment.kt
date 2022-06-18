package com.example.trubbi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.trubbi.R
import com.example.trubbi.data.EventResponse
import com.example.trubbi.holders.EventHolder
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class DetailsFragment : Fragment() {

    private lateinit var viewDetails: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDetails = inflater.inflate(R.layout.fragment_details, container, false)
        return viewDetails
    }

    override fun onStart() {
        super.onStart()
        val eventId = arguments?.getLong("eventId")
        viewDetails.findViewById<TextView>(R.id.textView3).text = eventId.toString()
    }

    private fun getEventById(id: Number, holder: EventHolder){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<EventResponse> = apiService.getEventById(id)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                        holder.itemView.findViewById<TextView>(R.id.textView3)
                    }
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }

}