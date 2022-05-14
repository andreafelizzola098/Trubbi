package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment(),LifecycleOwner {

    private lateinit var thisView:View
    private lateinit var recycler:RecyclerView
    var events : MutableList<Event> = ArrayList<Event>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        for (i in 1..5){
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Este evento es para toda la famiilia y niños ... Leer más...", "Vte. López", "https://picsum.photos/150?random=8"))
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Titeres y comida tradicional argentina, case ... Leer más...", "Vte. López", "https://picsum.photos/150?random=3"))
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Divertirte! Show gratuito de los Palmeras en ... Leer más...", "Vte. López", "https://picsum.photos/150?random=5"))
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Cine al aire libre y gratuito, comidas y más ... Leer más...", "Vte. López", "https://picsum.photos/150?random=1"))
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Feria artesanal, con show de malabares y una ... Leer más...", "Vte. López", "https://picsum.photos/150?random=7"))
            events.add(Event("Evento al que asistí $i", "21-12-22", "12hs","Torneo de Voley, inscripción abierta, hasta  ... Leer más...", "Vte. López", "https://picsum.photos/150?random=9"))
        }
        recycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycler.layoutManager = linearLayoutManager

        eventListAdapter = EventListAdapter(events){
                x -> onItemClick(x)
        }
        recycler.adapter = eventListAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_history, container, false)
        recycler = thisView.findViewById(R.id.historyRecycler)
        return thisView
    }

    private fun onItemClick(position: Int):Boolean{
        Snackbar.make(thisView, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }
}