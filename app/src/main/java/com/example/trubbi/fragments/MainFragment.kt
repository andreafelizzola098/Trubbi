package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.google.android.material.snackbar.Snackbar
import java.text.FieldPosition

class MainFragment : Fragment() {

    lateinit var main_view : View
    lateinit var recyclerView: RecyclerView
    var events : MutableList<Event> = ArrayList<Event>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        main_view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = main_view.findViewById(R.id.recycler_view)

        return main_view
    }

    override fun onStart() {
        super.onStart()

        for (i in 1..5){
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Leer más...", "Vte. López"))
        }
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        eventListAdapter = EventListAdapter(events){
            x -> onItemClick(x)
        }
        recyclerView.adapter = eventListAdapter
    }

    fun onItemClick(position: Int):Boolean{
        Snackbar.make(main_view, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}