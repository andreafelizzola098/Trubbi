package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.data.EventResponse
import com.example.trubbi.entities.Event
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider
import com.example.trubbi.services.ServiceBuilder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Response
import java.time.format.DateTimeFormatter

class MainFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mainView: View
    lateinit var recyclerView: RecyclerView
    private var events: MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var extendedFab: Button
    //start Search
    private lateinit var searchView: SearchView
    //end Search

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainView = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = mainView.findViewById(R.id.recycler_view)
        extendedFab = mainView.findViewById(R.id.extended_fab)
        //start Search
        searchView = requireActivity().findViewById(R.id.searchView) //SearchView
        searchView.setOnQueryTextListener(this)
        //end Search
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

        for (i in 1..20) {
            if (activity != null) {
                val event = EventProvider.random()
                events.add(event)
            }
        }
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        recyclerView.adapter = eventListAdapter

        extendedFab.setOnClickListener {
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            var query : String = query.toLowerCase()
            searchEventByTitle(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchEventByTitle(query: String) {
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<EventResponse>> = apiService.getSearchEvent(query)

        requestCall.enqueue(object: retrofit2.Callback<List<EventResponse>>{
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val eventResponse: List<EventResponse>? = response.body()
                    eventResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i]
                                val eventCard = buildEvent(event)
                                events.add(eventCard)
                                eventListAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, error: Throwable){
                Toast.makeText(context, "No existe el evento buscado", Toast.LENGTH_SHORT).show()
            }
        })
       // hideKeyboard()
    }

    fun buildEvent(event: EventResponse): EventCard {
        val startDate: String = DateTimeFormatter.ISO_INSTANT.format(
            java.time.Instant.ofEpochSecond(event.start_date)
        )
        return EventCard(
            event.title,
            //dateFormatt(startDate),
            isPublic(event.public),
            event.photo
        )
    }

    /*private fun hideKeyboard() {
        val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainView.windowToken, 0)
    }*/

    /*fun dateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:MM:SS")
        return formatter2.format(parsedDate)
    }*/

    fun isPublic(isPublic:Boolean): String{
        if (isPublic){
            return "publico"
        }else{
            return "privado"
        }
    }
}