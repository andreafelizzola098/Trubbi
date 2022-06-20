package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider

class SearchFragment : Fragment() {

    private lateinit var searchV: View
    private lateinit var recyclerSearch: RecyclerView
    private lateinit var msgSearch: TextView
    var events : MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View

    override fun onStart() {
        super.onStart()
        for (i in 1..20) {
            if (activity != null) {
                val event = EventProvider.random()
                events.add(event)
            }
        }
        recyclerSearch.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerSearch.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        recyclerSearch.adapter = eventListAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Buscado"
        }

        searchV = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerSearch = searchV.findViewById(R.id.recyclerViewSearch)
        msgSearch = searchV.findViewById(R.id.searchMsg)
        return searchV
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}