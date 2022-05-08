package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.entities.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.FieldPosition

class MainFragment : Fragment() {

    lateinit var main_view : View
    lateinit var recyclerView: RecyclerView
    var events : MutableList<Event> = ArrayList<Event>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var extendedFab : Button

    val items_categories = arrayOf("Artes Escénicas", "Arte y Cultura", "Deportes", "Familia y Niños", "Ferias y Conferencias", "Múica", "Otros", "Cercanos", "Gratuitos")

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
        extendedFab = main_view.findViewById(R.id.extended_fab)
        return main_view
    }

    override fun onStart() {
        super.onStart()

        for (i in 1..5){
            events.add(Event("Evento.$i", "21-12-22", "12hs","Este evento es para toda la famiilia y niños. Se dara en el Río de Vte. ... Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Titeres y comida tradicional argentina, casera, en el Río de Vte. desde ... Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Divertirte! Show gratuito de los Palmeras en la Costanera de Vte. López ... Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Cine al aire libre y gratuito, comidas y más para pasar con tu familia u... Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Feria artesanal, con show de malabares y una sorpresa para toda la famil... Leer más...", "Vte. López"))
            events.add(Event("Evento.$i", "21-12-22", "12hs","Torneo de Voley, inscripción abierta, hasta agotar cupos (150), material... Leer más...", "Vte. López"))
        }
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        eventListAdapter = EventListAdapter(events){
            x -> onItemClick(x)
        }
        recyclerView.adapter = eventListAdapter

        extendedFab.setOnClickListener {
            // Respond to Extended FAB click
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialogs_title))
                .setItems(items_categories) { dialog, which ->
                    // Respond to item chosen
                    
                }
                .show()
        }


    }

    fun onItemClick(position: Int):Boolean{
        Snackbar.make(main_view, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}