package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventDataAdapter
import com.example.trubbi.classes.HistFavSchedFragmentViewModel
import com.example.trubbi.providers.EventsProvider

class HistFavSchedFragment : Fragment(),LifecycleOwner {

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
        thisView = inflater.inflate(R.layout.fragment_hist_fav_sched, container, false)
        recycler = thisView.findViewById(R.id.multiDataRecycler)
        initRecyclerView()
        return thisView
    }

    private fun initRecyclerView(){
        val recyclerView = thisView.findViewById<RecyclerView>(R.id.multiDataRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        if(viewModel.number == 0){
            recyclerView.adapter = EventDataAdapter(EventsProvider.eventDataList)
        }else{
            recyclerView.adapter = EventDataAdapter(EventsProvider.favEventsDataList)
        }
    }






}