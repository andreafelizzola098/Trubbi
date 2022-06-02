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
import com.example.trubbi.entities.Event
import com.example.trubbi.model.EventProvider

class HistoryFragment : Fragment(), LifecycleOwner {

    private lateinit var thisView: View
    private lateinit var recycler: RecyclerView
    private var events: MutableList<Event> = ArrayList()
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

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}