package com.example.trubbi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.trubbi.R
import com.example.trubbi.data.EventResponse
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response
import android.widget.ImageButton
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.trubbi.commons.Commons
import com.example.trubbi.data.ScheduleDetails
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter

class DetailsFragment : Fragment() {

    private lateinit var viewDetails: View
    private lateinit var btnFav : ImageButton
    private lateinit var btnFavFill : ImageButton
    private lateinit var btnSchedule: ImageButton
    private lateinit var btnScheduleTint: ImageButton
    private var commons: Commons = Commons()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDetails = inflater.inflate(R.layout.fragment_details, container, false)

        btnFav = viewDetails.findViewById(R.id.imageButtonFav)
        btnFavFill = viewDetails.findViewById(R.id.imageButtonFavFill)

        btnSchedule = viewDetails.findViewById(R.id.imageButtonSchedule)
        btnScheduleTint = viewDetails.findViewById(R.id.imageButtonScheduleColor)

        return viewDetails
    }

    override fun onStart() {
        super.onStart()
        val eventId = arguments?.getLong("eventId")
        getEventById(eventId)
        getScheduleById(eventId)

        btnFavFill.setOnClickListener {
            btnFavFill.isVisible = false
            btnFav.isVisible = true
        }

        btnFav.setOnClickListener {
            btnFavFill.isVisible = true
            btnFav.isVisible = false
        }

        btnScheduleTint.setOnClickListener {
            btnScheduleTint.isVisible = false
            btnSchedule.isVisible = true
        }

        btnSchedule.setOnClickListener {
            btnScheduleTint.isVisible = true
            btnSchedule.isVisible = false
        }
    }

    private fun getEventById(id: Long?){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<EventResponse> = apiService.getEventById(eventId)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                        viewDetails.findViewById<TextView>(R.id.details_title).text = eventResponse.title
                        viewDetails.findViewById<TextView>(R.id.details_description).text = eventResponse.description
                        Picasso.get().load(eventResponse.photo).into(viewDetails.findViewById<ImageView>(R.id.details_image))
                        val startDate : String = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.start_date))
                        val formattDate = commons.dateFormatt(startDate)
                        viewDetails.findViewById<TextView>(R.id.details_date).text = formattDate
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, error: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }

    private fun getScheduleById(id: Long?){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<ScheduleDetails> = apiService.scheduleEvent(eventId)

        requestCall.enqueue(object: retrofit2.Callback<ScheduleDetails>{
            override fun onResponse(call: Call<ScheduleDetails>, response: Response<ScheduleDetails>){
                if(response.isSuccessful){
                    val eventResponse: ScheduleDetails? = response.body()
                    eventResponse?.let {
                        val isFavorite = eventResponse.favourite
                        val isSchedule = eventResponse.scheduled

                        if(isFavorite){
                            btnFavFill.isVisible = true
                        }else{
                            btnFav.isVisible = true
                        }

                        if(isSchedule){
                            btnScheduleTint.isVisible = true
                        }else{
                            btnSchedule.isVisible = true
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ScheduleDetails>, error: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }

    override fun onStop() {
        super.onStop()
        btnFav.isVisible = false
        btnFavFill.isVisible = false
        btnSchedule.isVisible = false
        btnScheduleTint.isVisible = false
        btnFav.isGone = true
        btnFavFill.isGone = true
        btnSchedule.isGone = true
        btnScheduleTint.isGone = true
    }

}