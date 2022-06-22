package com.example.trubbi.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.UpdateTourist
import com.example.trubbi.data.UpdateTouristRequest
import com.example.trubbi.data.UpdateTouristResponse
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.services.ServiceBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import java.time.format.DateTimeFormatter

class SettingsFragment : Fragment() {

    private lateinit var settingsView: View
    private lateinit var btnSave: Button
    private val key = "JWT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Configuración"
        }
        settingsView = inflater.inflate(R.layout.fragment_settings, container, false)
        btnSave = settingsView.findViewById(R.id.settings_save)
        return settingsView
    }

    override fun onStart() {
        super.onStart()
        val token = PreferenceManager.getDefaultSharedPreferences(this.context).getString(key,"")
        getTourist(token)

        btnSave.setOnClickListener {
            val name = settingsView.findViewById<TextInputLayout>(R.id.settings_name).editText?.text.toString()
            val updateTouristRequest = UpdateTouristRequest(name)
            updateTourist(token, updateTouristRequest)
        }
    }

    private fun getTourist(token: String?){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<UpdateTourist> = apiService.getTourist()

        requestCall.enqueue(object: retrofit2.Callback<UpdateTourist>{
            override fun onResponse(call: Call<UpdateTourist>, response: Response<UpdateTourist>){
                if(response.isSuccessful){
                    val touristResponse: UpdateTourist? = response.body()
                    touristResponse?.let {
                        val email: TextInputLayout = settingsView.findViewById(R.id.settings_email)
                        val name: TextInputLayout = settingsView.findViewById(R.id.settings_name)
                        email.editText?.setText(touristResponse.result.email)
                        name.editText?.setText(touristResponse.result.name)
                    }
                }
            }

            override fun onFailure(call: Call<UpdateTourist>, error: Throwable){
                Toast.makeText(
                    context, "Error al cargar el evento",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    private fun updateTourist(token: String?, updateTouristRequest: UpdateTouristRequest){
        val serviceBuilder = ServiceBuilder(token)
        val apiService: APIEventService = serviceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<UpdateTourist> = apiService.updateTourist(updateTouristRequest)

        requestCall.enqueue(object: retrofit2.Callback<UpdateTourist>{
            override fun onResponse(call: Call<UpdateTourist>, response: Response<UpdateTourist>){
                if(response.isSuccessful){
                    val touristResponse: UpdateTourist? = response.body()
                    touristResponse?.let {
                        Toast.makeText(
                            context, "Cambios exitosos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<UpdateTourist>, error: Throwable){
                Toast.makeText(
                    context, "Error al cargar el evento",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }
}