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

    private lateinit var toolBarSearchView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolBarSearchView = requireActivity().findViewById(R.id.searchView)
        toolBarSearchView.isVisible = false
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Mis Intereses"
        }
        return inflater.inflate(R.layout.fragment_interests, container, false)
    }

    override fun onStop() {
        super.onStop()
        toolBarSearchView.isVisible = true
    }
}