package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.data.EventResponse
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
    private lateinit var toolBarSearchView: View

    override fun onStart() {
        super.onStart()
        val historyId = arguments?.getLong("historyId")
        getTouristHistoryEvents(historyId)
        /*for (i in 1..20) {
            if (activity != null) {
                val event = EventProvider.random()
                events.add(event)
            }
        }*/
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
            (activity as MainActivity).supportActionBar?.title = "Historial"
        }
        thisView = inflater.inflate(R.layout.fragment_history, container, false)
        recycler = thisView.findViewById(R.id.historyRecycler)
        return thisView
    }

    private fun getTouristHistoryEvents(id: Long?){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val touristId = id as Number
        val requestCall: Call<List<EventResponse>> = apiService.getFavoritesEvents(touristId)

        requestCall.enqueue(object: retrofit2.Callback<List<EventResponse>>{
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val favoriteResponse: List<EventResponse>? = response.body()
                    favoriteResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i]
                                val eventCard = buildEvent(event)
                                events.add(eventCard)
                                eventListAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")
            }
        })
    }

    fun buildEvent(event: EventResponse): EventCard {
        val startDate: String = DateTimeFormatter.ISO_INSTANT.format(
            java.time.Instant.ofEpochSecond(event.start_date)
        )
        return EventCard(
            event.id.toLong(),
            event.title,
            dateFormatt(startDate),
            isPublic(event.public),
            "libertador 3000",
            event.photo
        )
    }

    fun isPublic(isPublic:Boolean): String{
        if (isPublic){
            return "publico"
        }else{
            return "privado"
        }
    }

    fun dateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:MM:SS")
        return formatter2.format(parsedDate)
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}