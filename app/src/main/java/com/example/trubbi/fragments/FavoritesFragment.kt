package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.example.trubbi.model.EventProvider

class FavoritesFragment : Fragment() {

    private lateinit var favorites_view: View
    private lateinit var favoriteRecyclerView: RecyclerView
    private var events: MutableList<Event> = ArrayList<Event>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Favoritos"
        }
        favorites_view = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoriteRecyclerView = favorites_view.findViewById(R.id.recycler_view_favorites)
        return favorites_view
    }

    override fun onStart() {
        super.onStart()
        for (i in 1..20) {
            if (activity != null) {
                val event = EventProvider.random()
                events.add(event)
            }
        }
        favoriteRecyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        favoriteRecyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        favoriteRecyclerView.adapter = eventListAdapter

    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}