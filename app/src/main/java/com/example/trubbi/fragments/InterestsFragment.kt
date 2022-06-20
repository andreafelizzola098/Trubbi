package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity

class InterestsFragment : Fragment() {

    private lateinit var categoryView : View

    private lateinit var toolBarSearchView: View
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
        return categoryView
    }

    override fun onStart() {
        super.onStart()

    }

  /*  private fun getCategoriesByTourist(id: Long?){
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val touristId = id as Number
        val requestCall: Call<TouristCategoryResponse> = apiService.getEventById(eventId)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                        viewDetails.findViewById<TextView>(R.id.details_title).text = eventResponse.title
                        viewDetails.findViewById<TextView>(R.id.details_description).text = eventResponse.description
                        Picasso.get().load(eventResponse.photo).into(viewDetails.findViewById<ImageView>(R.id.details_image))
                        val startDate : String = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.start_date))
                        val formattDate = dateFormatt(startDate)
                        viewDetails.findViewById<TextView>(R.id.details_date).text = formattDate
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, error: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
    }*/

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}