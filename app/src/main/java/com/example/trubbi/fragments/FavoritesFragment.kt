package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    lateinit var favorites_view: View
    lateinit var favoriteRecyclerView: RecyclerView
    var events: MutableList<Event> = ArrayList<Event>()
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
        favorites_view = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoriteRecyclerView = favorites_view.findViewById(R.id.recycler_view_favorites)
        return favorites_view
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

        eventListAdapter = EventListAdapter(events) { x ->
            onItemClick(x)
        }

        favoriteRecyclerView.adapter = eventListAdapter

    }

    fun onItemClick(position: Int):Boolean{
        Snackbar.make(favorites_view, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }
}