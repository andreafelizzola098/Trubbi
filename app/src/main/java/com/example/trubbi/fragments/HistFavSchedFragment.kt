package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventDataAdapter
import com.example.trubbi.providers.EventsProvider

class HistFavSchedFragment : Fragment() {

    private lateinit var thisView:View
    private lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_hist_fav_sched, container, false)
        recycler = thisView.findViewById(R.id.multiDataRecycler)
        initRecyclerView()
        return thisView
    }

    private fun initRecyclerView(){
        val recyclerView = thisView.findViewById<RecyclerView>(R.id.multiDataRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = EventDataAdapter(EventsProvider.eventDataList)
    }






}