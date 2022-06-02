package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainFragment : Fragment() {

    private lateinit var mainView: View
    lateinit var recyclerView: RecyclerView
    private var events: MutableList<Event> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var extendedFab: Button
    private var listener: ((item: Event) -> Unit)? = null

    private val itemsCategories = arrayOf(
        "Artes Escénicas",
        "Arte y Cultura",
        "Deportes",
        "Familia y Niños",
        "Ferias y Conferencias",
        "Música",
        "Otros",
        "Cercanos"
    )
    fun setOnItemClickListener(listener: (item: Event) -> Unit) {
        this.listener = listener
    }

    private val items_categories = arrayOf("Artes Escénicas", "Arte y Cultura", "Deportes", "Familia y Niños", "Ferias y Conferencias", "Música", "Otros", "Cercanos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainView = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = mainView.findViewById(R.id.recycler_view)
        extendedFab = mainView.findViewById(R.id.extended_fab)
        return mainView
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        val toolbar =
            (activity as MainActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)

        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            (activity as MainActivity).drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

        for (i in 1..5) {
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Este evento es para toda la famiilia y niños ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=8"
                )
            )
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Titeres y comida tradicional argentina, case ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=3"
                )
            )
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Divertirte! Show gratuito de los Palmeras en ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=5"
                )
            )
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Cine al aire libre y gratuito, comidas y más ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=1"
                )
            )
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Feria artesanal, con show de malabares y una ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=7"
                )
            )
            events.add(
                Event(
                    "Evento.$i",
                    "21-12-22",
                    "12hs",
                    "Torneo de Voley, inscripción abierta, hasta  ... Leer más...",
                    "Vte. López",
                    "https://picsum.photos/150?random=9"
                )
            )
        }
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        recyclerView.adapter = eventListAdapter

        extendedFab.setOnClickListener {
            // Respond to Extended FAB click
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialogs_title))
                .setItems(itemsCategories) { _, which ->
                    when (which) {
                        0 -> onItemClick(0)
                        1 -> onItemClick(1)
                        2 -> onItemClick(2)
                        3 -> onItemClick(3)
                        4 -> onItemClick(4)
                        5 -> onItemClick(5)
                        6 -> onItemClick(6)
                        7 -> onItemClick(7)
                        8 -> onItemClick(8)
                    }
                    
                }
                .show()
        }


    }

    private fun onItemClick(position: Int): Boolean {
        val actionCategory =
            MainFragmentDirections.actionMainFragmentToCategoriesFragment(itemsCategories[position])
        mainView.findNavController().navigate(actionCategory)
        return true
    }

}