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

class MyEventsFragment : Fragment() {

    private lateinit var thisView: View
    private lateinit var recycler: RecyclerView
    private var events: MutableList<Event> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var toolBarSearchView: View

    override fun onStart() {
        super.onStart()
        for (i in 1..5) {
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Este evento es para toda la famiilia y niños ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=8"
                )
            )
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Titeres y comida tradicional argentina, case ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=3"
                )
            )
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Divertirte! Show gratuito de los Palmeras en ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=5"
                )
            )
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Cine al aire libre y gratuito, comidas y más ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=1"
                )
            )
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Feria artesanal, con show de malabares y una ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=7"
                )
            )
            events.add(
                Event(
                    "Mi Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Torneo de Voley, inscripción abierta, hasta  ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=9"
                )
            )
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
            (activity as MainActivity).supportActionBar?.title = "Agendados"
        }
        thisView = inflater.inflate(R.layout.fragment_my_events, container, false)
        recycler = thisView.findViewById(R.id.myEventsRecycler)
        return thisView
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}