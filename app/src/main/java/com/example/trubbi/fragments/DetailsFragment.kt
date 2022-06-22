package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
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
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.commons.Commons
import com.example.trubbi.data.Schedule
import com.example.trubbi.data.ScheduleDetails
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var viewDetails: View
    private lateinit var btnFav : ImageButton
    private lateinit var btnFavFill : ImageButton
    private lateinit var btnSchedule: ImageButton
    private lateinit var btnScheduleTint: ImageButton
    private var commons: Commons = Commons()
    private val key = "JWT"
    private lateinit var btnOpinions: ExtendedFloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Evento"
        }

        viewDetails = inflater.inflate(R.layout.fragment_details, container, false)

        btnFav = viewDetails.findViewById(R.id.imageButtonFav)
        btnFavFill = viewDetails.findViewById(R.id.imageButtonFavFill)

        btnSchedule = viewDetails.findViewById(R.id.imageButtonSchedule)
        btnScheduleTint = viewDetails.findViewById(R.id.imageButtonScheduleColor)
        btnOpinions = viewDetails.findViewById(R.id.btnOpinion)

        return viewDetails
    }

    override fun onStart() {
        super.onStart()
        val eventId = arguments?.getLong("eventId")
        val token = PreferenceManager.getDefaultSharedPreferences(this.context).getString(key,"")
        getEventById(eventId, token)
        getScheduleEvents(eventId, token)
        getFavoriteEvents(eventId, token)

        btnOpinions.setOnClickListener {
            val action = eventId?.let { it1 ->
                DetailsFragmentDirections.actionDetailsFragmentToOpinionSurveyFragment(
                    it1
                )
            }
            if (action != null) {
                viewDetails.findNavController().navigate(action)
            }
        }

        btnFavFill.setOnClickListener {
            deleteFavoriteEvent(eventId,token)
        }

        btnFav.setOnClickListener {
            favoriteEvent(eventId,token)
        }

        btnScheduleTint.setOnClickListener {
            deleteScheduleEvent(eventId,token)
        }

        btnSchedule.setOnClickListener {
            scheduleEvent(eventId,token)
        }
    }

    private fun getEventById(id: Long?, token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<EventResponse> = apiService.getEventById(eventId)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                        viewDetails.findViewById<TextView>(R.id.details_title).text = eventResponse.title
                        viewDetails.findViewById<TextView>(R.id.details_description).text = eventResponse.description
                        viewDetails.findViewById<TextView>(R.id.details_address).text = eventResponse.address
                        Picasso.get().load(eventResponse.photo).into(viewDetails.findViewById<ImageView>(R.id.details_image))
                        val startDate : String = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.start_date))
                        val formattDate = commons.dateFormatt(startDate)
                        viewDetails.findViewById<TextView>(R.id.details_date).text = formattDate
                        val endDate : String = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.end_date))
                        val parsedEndDate = LocalDateTime.parse(commons.endDateFormatt(endDate))
                        val currentDate = LocalDateTime.now()
                        if(parsedEndDate < currentDate){
                            btnOpinions.isVisible = true
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, error: Throwable){
                Toast.makeText(
                    context, "Error al cargar el evento",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    private fun getScheduleEvents(id: Long?, token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val eventId = id as Number
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<Schedule>> = apiService.getScheduleEvents()

        requestCall.enqueue(object: retrofit2.Callback<List<Schedule>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>){
                if(response.isSuccessful){
                    val scheduleResponse: List<Schedule>? = response.body()
                    scheduleResponse?.let {
                        btnSchedule.isVisible = true
                        for(i in it.indices){
                            if(it[i].event.id.toLong() == eventId){
                                val scheduleEvent = it[i]
                                if(scheduleEvent.scheduled){
                                    btnScheduleTint.isVisible = true
                                    btnSchedule.isGone = true
                                } else {
                                    btnSchedule.isVisible = true
                                    btnScheduleTint.isGone = true
                                }
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                println("asd")
                Toast.makeText(
                    context, "Error al cargar los eventos agendados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getFavoriteEvents(id: Long?, token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val eventId = id as Number
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<Schedule>> = apiService.getFavoritesEvents()

        requestCall.enqueue(object: retrofit2.Callback<List<Schedule>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>){
                if(response.isSuccessful){
                    val scheduleResponse: List<Schedule>? = response.body()
                    scheduleResponse?.let {
                        btnFav.isVisible = true
                        for(i in it.indices){
                            if(it[i].event.id.toLong() == eventId){
                                val scheduleEvent = it[i]
                                if(scheduleEvent.favourite){
                                   btnFavFill.isVisible = true
                                    btnFav.isGone = true
                                } else {
                                    btnFav.isVisible = true
                                    btnFavFill.isGone = true
                                }
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                println("")
                Toast.makeText(
                    context, "Error al cargar los eventos favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun scheduleEvent(id: Long?,token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<ScheduleDetails> = apiService.scheduleEvent(eventId)

        requestCall.enqueue(object: retrofit2.Callback<ScheduleDetails>{
            override fun onResponse(call: Call<ScheduleDetails>, response: Response<ScheduleDetails>){
                if(response.isSuccessful){
                    val eventResponse: ScheduleDetails? = response.body()
                    eventResponse?.let {
                            btnScheduleTint.isVisible = true
                            btnSchedule.isGone = true
                    }
                }
            }

            override fun onFailure(call: Call<ScheduleDetails>, error: Throwable){
                Toast.makeText(
                    context, "Error al agendar a un evento",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun deleteScheduleEvent(id: Long?,token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<ScheduleDetails> = apiService.deleteScheduleEvent(eventId)

        requestCall.enqueue(object: retrofit2.Callback<ScheduleDetails>{
            override fun onResponse(call: Call<ScheduleDetails>, response: Response<ScheduleDetails>){
                if(response.isSuccessful){
                    val eventResponse: ScheduleDetails? = response.body()
                    eventResponse?.let {
                        btnSchedule.isVisible = true
                        btnScheduleTint.isGone = true
                    }
                }
            }

            override fun onFailure(call: Call<ScheduleDetails>, error: Throwable){
                Toast.makeText(
                    context, "Error al desagendar un evento",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun favoriteEvent(id: Long?,token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<ScheduleDetails> = apiService.favoriteEvent(eventId)

        requestCall.enqueue(object: retrofit2.Callback<ScheduleDetails>{
            override fun onResponse(call: Call<ScheduleDetails>, response: Response<ScheduleDetails>){
                if(response.isSuccessful){
                    val eventResponse: ScheduleDetails? = response.body()
                    eventResponse?.let {
                            btnFavFill.isVisible = true
                            btnFav.isGone = true
                    }
                }
            }

            override fun onFailure(call: Call<ScheduleDetails>, error: Throwable){
                Toast.makeText(
                    context, "Error al agregar un favorito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun deleteFavoriteEvent(id: Long?,token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val eventId = id as Number
        val requestCall: Call<ScheduleDetails> = apiService.deleteFavoriteEvent(eventId)

        requestCall.enqueue(object: retrofit2.Callback<ScheduleDetails>{
            override fun onResponse(call: Call<ScheduleDetails>, response: Response<ScheduleDetails>){
                if(response.isSuccessful){
                    val eventResponse: ScheduleDetails? = response.body()
                    eventResponse?.let {
                        btnFavFill.isGone = true
                        btnFav.isVisible = true
                    }
                }
            }

            override fun onFailure(call: Call<ScheduleDetails>, error: Throwable){
                println("")
                Toast.makeText(
                    context, "Error al quitar un favorito",
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("evento1")
        println("Te suscribiste al topico")
    }

}