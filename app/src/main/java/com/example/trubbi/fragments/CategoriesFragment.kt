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
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider

class CategoriesFragment : Fragment() {

    private lateinit var categoryView : View
    private lateinit var categoryRecyclerView: RecyclerView
    private var events : MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View

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
        for (i in 1..20) {
            if (activity != null) {
                val event = EventProvider.random()
                events.add(event)
            }
        }

        categoryRecyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        categoryRecyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        categoryRecyclerView.adapter = eventListAdapter

    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}