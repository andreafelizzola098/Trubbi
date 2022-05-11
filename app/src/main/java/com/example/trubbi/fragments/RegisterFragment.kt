package com.example.trubbi.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.trubbi.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private lateinit var v : View
    private lateinit var txtLogin : TextView
    private lateinit var mailAuth : FirebaseAuth
    private lateinit var btnRegister : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        v = inflater.inflate(R.layout.fragment_register, container, false)

        txtLogin = v.findViewById(R.id.txtLogin)

        btnRegister= v.findViewById(R.id.btnRegister)

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

            val email : TextInputLayout = v.findViewById(R.id.emailRegister)
            val password: TextInputLayout = v.findViewById(R.id.passRegister1)

            createUser(email.editText?.text.toString(), password.editText?.text.toString())

        }
    }

    private fun createUser(email : String, password : String) {
        mailAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(context, "User created successfully",
                        Toast.LENGTH_SHORT).show()
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    v.findNavController().navigate(action)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "User creation failed" ,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}

