package com.example.trubbi.providers

import com.example.trubbi.model.EventCard

class EventProvider {

    companion object {
        fun random(): EventCard {
            val position = (0..5).random()
            return events[position]
        }

        private val events = listOf(
                EventCard(
                        "Evento.1",
                        "04/05/2022 19:00   -   4hs",
                        "Publico",
                        "Calle Falsa 123, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=11"
                ),
                EventCard(
                        "Evento.2",
                        "04/05/2022 19:00   -   4hs",
                        "Privado",
                        "False Street 321, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=16"
                ),
                EventCard(
                        "Evento.3",
                        "04/05/2022 19:00   -   4hs",
                        "Publico",
                        "Maipu 3443, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=21"
                ),
                EventCard(
                        "Evento.4",
                        "04/05/2022 19:00   -   4hs",
                        "Privado",
                        "Sarmiento 532, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=10"
                ),
                EventCard(
                        "Evento.5",
                        "04/05/2022 19:00   -   4hs",
                        "Publico",
                        "Florida 2010, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=2"
                ),
                EventCard(
                        "Evento.6",
                        "04/05/2022 19:00   -   4hs",
                        "Privado",
                        "Florida 2010, 1636, Olivos, Provincia de Buenos Aires",
                        "https://picsum.photos/150?random=8"
                )
        )
    }
}