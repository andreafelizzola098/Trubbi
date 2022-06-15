package com.example.trubbi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trubbi.model.EventCard
import com.example.trubbi.providers.EventProvider

class EventViewModel: ViewModel() {

    val events = MutableLiveData<EventCard>()

    fun randomEvent(){
        val currentEvent = EventProvider.random()
        events.postValue(currentEvent)
    }
}