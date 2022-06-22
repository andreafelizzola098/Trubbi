package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.navigation.findNavController
import com.example.trubbi.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.lang.IllegalArgumentException

class ForgotPasswordFragment : Fragment() {
    private lateinit var v: View
    private lateinit var btnForgot: MaterialButton
    private lateinit var txtBackLogin: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        btnForgot = v.findViewById(R.id.btnForgot)
        txtBackLogin = v.findViewById(R.id.txtBackLogin)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnForgot.setOnClickListener {
            val email: TextInputLayout = v.findViewById(R.id.emailForgot)
            try {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.editText?.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context, "email enviado para restaurar la contraseña!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val action =
                                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
                            v.findNavController().navigate(action)

                        } else {
                            Toast.makeText(
                                context, task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    context, "Por favor, ingrese un email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        txtBackLogin.setOnClickListener {
            val action =
                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
            v.findNavController().navigate(action)
        }
    }

}