package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.commons.Commons
import com.example.trubbi.data.EventResponse
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchV: View
    private lateinit var recyclerSearch: RecyclerView
    private lateinit var msgSearch: TextView
    private var commons: Commons = Commons()
    private var events: MutableList<EventCard> = ArrayList()
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchV = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerSearch = searchV.findViewById(R.id.recyclerViewSearch)
        msgSearch = searchV.findViewById(R.id.searchMsg)
        //val text = CategoriesFragmentArgs.fromBundle(requireArguments()).namecategory
        return searchV
    }

    private fun getEventsByTitle(query: String?, token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<EventResponse>> = apiService.getEventByTitle(query)

        requestCall.enqueue(object: retrofit2.Callback<List<EventResponse>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val eventResponse: List<EventResponse>? = response.body()
                    eventResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i]
                                val eventCard = commons.buildEvent(event)
                                events.add(eventCard)
                            }
                        }
                        eventListAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<List<EventResponse>>, error: Throwable){
                println("")
                Toast.makeText(
                    context, "Error al cargar los eventos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val token = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext).getString("JWT","")
        val query = arguments?.getString("query")
        getEventsByTitle(query, token)
    }
}