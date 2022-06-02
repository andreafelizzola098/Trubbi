package com.example.trubbi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trubbi.entities.Event
import com.example.trubbi.model.EventProvider

class EventViewModel: ViewModel() {

    val events = MutableLiveData<Event>()

    fun randomEvent(){
        val currentEvent = EventProvider.random()
        events.postValue(currentEvent)
    }
}