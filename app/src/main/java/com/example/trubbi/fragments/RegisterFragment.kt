package com.example.trubbi.fragments

import android.content.ContentValues.TAG
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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
                val email: TextInputLayout = v.findViewById(R.id.emailRegister)
                val password: TextInputLayout = v.findViewById(R.id.passRegister1)
                val password2: TextInputLayout = v.findViewById(R.id.passRegister2)

                val emailString: String = email.editText?.text.toString()
                val passwordString: String = password.editText?.text.toString()
                val password2String: String = password2.editText?.text.toString()

                if (passwordString.isNotEmpty() == password2String.isNotEmpty()) {
                    createUser(emailString, passwordString)

                } else if (passwordString.isNotEmpty() != password2String.isNotEmpty()) {
                    Toast.makeText(
                        context, "Password mismatch, please enter the same password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    context, "All fields must be completed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUser(email: String, password: String) {
        mailAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        context, "User created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    v.findNavController().navigate(action)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "User creation failed, wrong email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

