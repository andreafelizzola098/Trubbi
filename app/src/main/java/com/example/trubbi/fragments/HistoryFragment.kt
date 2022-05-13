package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventDataAdapter
import com.example.trubbi.classes.HistFavSchedFragmentViewModel
import com.example.trubbi.providers.EventsProvider

class HistoryFragment : Fragment(),LifecycleOwner {

    private lateinit var thisView:View
    private lateinit var recycler:RecyclerView
    private lateinit var viewModel:HistFavSchedFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HistFavSchedFragmentViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_history, container, false)
        recycler = thisView.findViewById(R.id.historyRecycler)
        initRecyclerView()
        return thisView
    }

    private fun initRecyclerView(){
        val recyclerView = thisView.findViewById<RecyclerView>(R.id.historyRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = EventDataAdapter(EventsProvider.historyDataList)

    }






}