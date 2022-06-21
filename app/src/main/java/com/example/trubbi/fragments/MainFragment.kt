package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.adapters.EventListAdapter
import com.example.trubbi.data.EventResponse
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.model.EventCard
import com.example.trubbi.commons.Commons
import com.example.trubbi.data.CategoryList
import com.example.trubbi.data.CategoryResponse
import com.example.trubbi.services.ServiceBuilder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Response

class MainFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mainView: View
    lateinit var recyclerView: RecyclerView
    private var events: MutableList<EventCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var extendedFab: Button
    private var commons: Commons = Commons()
    private var categoryTitles = arrayOfNulls<String>(0)
    private var categoriesResponse: MutableList<CategoryResponse> = ArrayList()

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
        val searchView =  (activity as MainActivity).findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(this)

        getEvents()
        getCategories()
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        eventListAdapter = EventListAdapter(events)
        recyclerView.adapter = eventListAdapter

    }

    private fun onItemClick(position: Int): Boolean {
        val categoryId = getCategoryId(position)
        val actionCategory =
            MainFragmentDirections.actionMainFragmentToCategoriesFragment(categoryId, categoryTitles[position])
        mainView.findNavController().navigate(actionCategory)
        return true
    }

    private fun getCategoryId(position: Int): Long {
        var categoryId: Number = 0
        for(i in 0 until categoriesResponse.size){
            if(categoryTitles[position] == categoriesResponse[i].name)
                categoryId = categoriesResponse[i].id
        }
        return categoryId.toLong()
    }

    private fun getEvents(){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<EventResponse>> = apiService.getEvents()

        requestCall.enqueue(object: retrofit2.Callback<List<EventResponse>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val eventResponse: List<EventResponse>? = response.body()
                    eventResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i]
                                val eventCard = commons.buildEvent(event)
                                events.add(eventCard)
                            }
                        }
                        eventListAdapter.notifyDataSetChanged()
                    }
                }
            }


            override fun onFailure(call: Call<List<EventResponse>>, error: Throwable){
                println("")
                Toast.makeText(
                    context, "Error al cargar los eventos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getCategories(){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<CategoryList> = apiService.getCategories()

        requestCall.enqueue(object: retrofit2.Callback<CategoryList>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>){
                if(response.isSuccessful){
                    val categoryResponse: CategoryList? = response.body()
                    categoryResponse?.let {
                        categoryTitles = arrayOfNulls(it.categoryList.size)
                        for(i in it.categoryList.indices){
                            if (activity != null) {
                                categoriesResponse.add( it.categoryList[i])
                                categoryTitles[i] =  it.categoryList[i].name
                            }
                        }
                        fillCategoriesMenu(categoryTitles)
                    }
                }
            }


            override fun onFailure(call: Call<CategoryList>, error: Throwable){
                println("")
                Toast.makeText(
                    context, "Error al cargar los eventos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun fillCategoriesMenu(categoryTitles: Array<String?>) {
        extendedFab.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialogs_title))
                .setItems(categoryTitles) { _, which ->
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            val query : String = query.toLowerCase()
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
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val eventResponse: List<EventResponse>? = response.body()
                    eventResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                                val event: EventResponse = it[i]
                                val eventCard = commons.buildEvent(event)
                                events.add(eventCard)
                            }
                        }
                        eventListAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, error: Throwable){
                Toast.makeText(context, "No existe el evento buscado", Toast.LENGTH_SHORT).show()
            }
        })
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainView.windowToken, 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPause() {
        super.onPause()
        events = ArrayList()
        eventListAdapter.notifyDataSetChanged()
    }
}