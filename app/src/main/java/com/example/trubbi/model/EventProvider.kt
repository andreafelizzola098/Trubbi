package com.example.trubbi.model

import com.example.trubbi.entities.Event

class EventProvider {

    companion object {
        fun random():Event{
            val position =(0..5).random()
            return events[position]
        }

        private val events = listOf(
            Event(
                "Evento.1",
                "21-12-22",
                "12hs",
                "Este evento es para toda la famiilia y niños ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=11"
            ),
            Event(
                "Evento.2",
                "21-12-22",
                "12hs",
                "Titeres y comida tradicional argentina, case ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=16"
            ),
            Event(
                "Evento.3",
                "21-12-22",
                "12hs",
                "Divertirte! Show gratuito de los Palmeras en ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=21"
            ),
            Event(
                "Evento.4",
                "21-12-22",
                "12hs",
                "Cine al aire libre y gratuito, comidas y más ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=10"
            ),
            Event(
                "Evento.5",
                "21-12-22",
                "12hs",
                "Feria artesanal, con show de malabares y una ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=2"
            ),
            Event(

                "Evento.6",
                "21-12-22",
                "12hs",
                "Torneo de Voley, inscripción abierta, hasta  ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=8"
            )
        )
    }
}