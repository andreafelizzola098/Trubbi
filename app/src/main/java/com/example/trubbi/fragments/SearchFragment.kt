package com.example.trubbi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R

class SearchFragment : Fragment() {

    private lateinit var searchV: View
    private lateinit var recyclerSearch: RecyclerView
    private lateinit var msgSearch: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchV = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerSearch = searchV.findViewById(R.id.recyclerViewSearch)
        msgSearch = searchV.findViewById(R.id.searchMsg)
        return searchV
    }
}