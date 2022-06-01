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
        for (i in 1..5) {
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Este evento es para toda la famiilia y niños ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=11"
                )
            )
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Titeres y comida tradicional argentina, case ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=16"
                )
            )
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Divertirte! Show gratuito de los Palmeras en ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=21"
                )
            )
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Cine al aire libre y gratuito, comidas y más ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=10"
                )
            )
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Feria artesanal, con show de malabares y una ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=2"
                )
            )
            events.add(
                Event(
                    "Favorito.$i",
                    "21-12-22",
                    "12hs",
                    "Torneo de Voley, inscripción abierta, hasta  ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=8"
                )
            )
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