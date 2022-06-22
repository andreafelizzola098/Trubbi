package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.commons.Commons
import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.Schedule
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class MyEventsFragment : Fragment() {

    private lateinit var thisView: View
    private lateinit var recycler: RecyclerView
    private var events: MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View
    private var commons: Commons = Commons()
    private val key = "JWT"

    override fun onStart() {
        super.onStart()
        val token = PreferenceManager.getDefaultSharedPreferences(this.context).getString(key,"")
        getScheduleEvents(token)
        recycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycler.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        recycler.adapter = eventListAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Agendados"
        }
        thisView = inflater.inflate(R.layout.fragment_my_events, container, false)
        recycler = thisView.findViewById(R.id.myEventsRecycler)
        return thisView
    }

    private fun getScheduleEvents(token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<Schedule>> = apiService.getScheduleEvents()

        requestCall.enqueue(object: retrofit2.Callback<List<Schedule>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>){
                if(response.isSuccessful){
                    val scheduleResponse: List<Schedule>? = response.body()
                    scheduleResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i].event
                                val eventCard = commons.buildEvent(event)
                                events.add(eventCard)
                            }
                        }
                        eventListAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Toast.makeText(
                    context, "Error al cargar los eventos agendados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}