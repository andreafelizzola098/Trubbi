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
import androidx.lifecycle.LifecycleOwner
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
import java.time.format.DateTimeFormatter

class HistoryFragment : Fragment(), LifecycleOwner {

    private lateinit var thisView:View
    private lateinit var recycler:RecyclerView
    var events : MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private var commons: Commons = Commons()
    private val key = "JWT"

    override fun onStart() {
        super.onStart()
        val token = PreferenceManager.getDefaultSharedPreferences(this.context).getString(key,"")
        getTouristHistoryEvents(token)
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
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Historial"
        }
        thisView = inflater.inflate(R.layout.fragment_history, container, false)
        recycler = thisView.findViewById(R.id.historyRecycler)
        return thisView
    }

    private fun getTouristHistoryEvents(token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<Schedule>> = apiService.getHistoryEvents()

        requestCall.enqueue(object: retrofit2.Callback<List<Schedule>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>){
                if(response.isSuccessful){
                    val favoriteResponse: List<Schedule>? = response.body()
                    favoriteResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val eventCard = commons.buildEvent(it[i].event)
                                events.add(eventCard)
                            }
                        }
                        eventListAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Toast.makeText(
                    context, "Error al cargar el historial",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPause() {
        super.onPause()
        events = ArrayList()
        eventListAdapter.notifyDataSetChanged()
    }
}