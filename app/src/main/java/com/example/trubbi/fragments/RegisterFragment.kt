package com.example.trubbi.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.trubbi.R
import com.example.trubbi.activities.MainActivity
import com.example.trubbi.data.LoginTouristResponse
import com.example.trubbi.data.RegisterTouristResponse
import com.example.trubbi.interfaces.APILoginService
import com.example.trubbi.services.LoginServiceBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Response

class RegisterFragment : Fragment() {
    private lateinit var v: View
    private lateinit var txtLogin: TextView
    private lateinit var mailAuth: FirebaseAuth
    private lateinit var btnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        v = inflater.inflate(R.layout.fragment_register, container, false)
        txtLogin = v.findViewById(R.id.txtLogin)
        btnRegister = v.findViewById(R.id.btnRegister)
        mailAuth = Firebase.auth
        return v
    }

    override fun onStart() {
        super.onStart()

        txtLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            v.findNavController().navigate(action)
        }

        btnRegister.setOnClickListener {
            try {
                val name: TextInputLayout = v.findViewById(R.id.nameRegister)
                val email: TextInputLayout = v.findViewById(R.id.emailRegister)
                val password: TextInputLayout = v.findViewById(R.id.passRegister1)
                val password2: TextInputLayout = v.findViewById(R.id.passRegister2)
                val emailString: String = email.editText?.text.toString()
                val passwordString: String = password.editText?.text.toString()
                val password2String: String = password2.editText?.text.toString()
                val nameString: String = name.editText?.text.toString()
                if (passwordString.isNotEmpty() == password2String.isNotEmpty()) {
                    //createUser(emailString, passwordString)
                        val user = RegisterTouristResponse(emailString,passwordString,nameString)
                        createTourist(user)

                } else if (passwordString.isNotEmpty() != password2String.isNotEmpty()) {
                    Toast.makeText(
                        context, "Las contraseñas no coinciden, por favor reingrese la contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    context, "Debe completar todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUser(email: String, password: String) {
        mailAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context, "Usuario creado correctamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    v.findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        context, "Falló registro, formato de email o contraseña incorrecto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun createTourist(user: RegisterTouristResponse){
        val apiService = LoginServiceBuilder.buildService(APILoginService::class.java)
        apiService.createTourist(user).enqueue(
            object : retrofit2.Callback<RegisterTouristResponse> {
                override fun onFailure(call: Call<RegisterTouristResponse>, t: Throwable) {
                    Toast.makeText(
                        context, "Error!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<RegisterTouristResponse>, response: Response<RegisterTouristResponse>) {
                    val loggedUser = response.body()
                    if(response.errorBody() == null){
                        Toast.makeText(context, "Cuenta creada correctamente!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "No estas registrado", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
}

