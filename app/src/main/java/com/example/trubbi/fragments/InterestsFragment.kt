package com.example.trubbi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.data.CategoryList
import com.example.trubbi.data.CategoryResponse
import com.example.trubbi.data.EventResponse
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class InterestsFragment : Fragment() {

    private lateinit var categoryView : View
    private lateinit var toolBarSearchView: View
    private lateinit var linearLayout: LinearLayout
    private lateinit var interestBox: ConstraintLayout
    private var categoriesResponse: MutableList<CategoryResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false
        categoryView = inflater.inflate(R.layout.fragment_interests, container, false)
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Mis Intereses"
        }
        linearLayout = categoryView.findViewById(R.id.linearListContainer)
        interestBox = categoryView.findViewById(R.id.interestBox)
        getCategories()
        return categoryView
    }

    override fun onStart() {
        super.onStart()
        getTouristCategories()
    }

    private fun getTouristCategories(){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<CategoryResponse>> = apiService.getTouristCategories()

        requestCall.enqueue(object: retrofit2.Callback<List<CategoryResponse>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<CategoryResponse>>, response: Response<List<CategoryResponse>>){
                if(response.isSuccessful){
                    val categoryResponse: List<CategoryResponse>? = response.body()
                    categoryResponse?.let {
                        for(i in it.indices){
                            if (activity != null) {
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<CategoryResponse>>, error: Throwable){
                Toast.makeText(
                    context, "Error al cargar sus categorías de interés",
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
                        for(i in it.categoryList.indices){
                            if (activity != null) {
                                categoriesResponse.add(it.categoryList[i])
                                linearLayout.addView(interestBox)
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<CategoryList>, error: Throwable){
                Toast.makeText(
                    context, "Error al cargar las categorías",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}