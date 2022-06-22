package com.example.trubbi.commons

import com.example.trubbi.data.EventResponse
import com.example.trubbi.model.EventCard
import java.time.format.DateTimeFormatter

class Commons {

     fun buildEvent(event: EventResponse): EventCard {
        val startDate: String = DateTimeFormatter.ISO_INSTANT.format(
                java.time.Instant.ofEpochSecond(event.start_date)
        )
        return EventCard(
                event.id.toLong(),
                event.title,
                dateFormatt(startDate),
                isPublic(event.public),
        "maipu 1020",
                event.photo
        )
    }

    fun isPublic(isPublic:Boolean): String{
        return if (isPublic) "Publico" else "Privado"
    }

    fun dateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:MM:SS")
        return formatter2.format(parsedDate)
    }

    fun endDateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return formatter2.format(parsedDate)
    }
}