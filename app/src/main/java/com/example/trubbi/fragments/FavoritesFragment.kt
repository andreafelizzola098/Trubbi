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

    private lateinit var favoritesView: View
    private lateinit var favoriteRecyclerView: RecyclerView
    private var events: MutableList<Event> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Favoritos"
        }
        favoritesView = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoriteRecyclerView = favoritesView.findViewById(R.id.recycler_view_favorites)
        return favoritesView
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