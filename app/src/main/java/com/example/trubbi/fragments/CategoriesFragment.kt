package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class CategoriesFragment : Fragment() {

    private lateinit var categoryView : View
    private lateinit var categoryRecyclerView: RecyclerView
    private var events : MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View
    private var commons: Commons = Commons()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false
        val text = CategoriesFragmentArgs.fromBundle(requireArguments()).namecategory
        if(activity != null){
            (activity as MainActivity).supportActionBar?.title = " $text"
        }
        categoryView = inflater.inflate(R.layout.fragment_categories, container, false)
        categoryRecyclerView = categoryView.findViewById(R.id.recycler_view_categories)

        return categoryView
    }

    override fun onStart() {
        super.onStart()
        val categoryId = CategoriesFragmentArgs.fromBundle(requireArguments()).categoryId
        getCategoryEvents(categoryId)
        categoryRecyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        categoryRecyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        categoryRecyclerView.adapter = eventListAdapter

    }

    private fun getCategoryEvents(categoryId: Long) {
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<EventResponse>> = apiService.getEventsByCategory(categoryId)

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
                Toast.makeText(
                    context, "Error al cargar los eventos",
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

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}