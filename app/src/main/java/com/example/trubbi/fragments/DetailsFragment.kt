package com.example.trubbi.fragments

import android.graphics.drawable.Drawable
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
import android.net.Uri;
import android.widget.ImageButton
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.trubbi.data.Schedule
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var viewDetails: View
    private lateinit var btnFav : ImageButton
    private lateinit var btnFavFill : ImageButton
    private lateinit var btnSchedule: ImageButton
    private lateinit var btnScheduleTint: ImageButton

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
        val isFavorite = false
        val isSchedule = false

        if(isFavorite){
            btnFavFill.isVisible = true
        }else{
            btnFav.isVisible = true
        }

        if(isSchedule){
            btnScheduleTint.isGone = true
        }else{
            btnSchedule.isVisible = true
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
                        val startDate : String = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.start_date))
                        val formattDate = dateFormatt(startDate)
                        viewDetails.findViewById<TextView>(R.id.details_date).text = formattDate
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, error: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }

    fun dateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:MM:SS")
        return formatter2.format(parsedDate)
    }

    override fun onStop() {
        super.onStop()
        btnFav.isGone = true
        btnFavFill.isGone = true
        btnSchedule.isGone = true
        btnScheduleTint.isGone = true
    }

}