package com.example.trubbi.providers

import com.example.trubbi.entities.EventData

class EventsProvider {
    companion object {
        val favEventsDataList = listOf<EventData>(
            EventData(
                "Favorito 1",
                "21-12-22",
                "12hs",
                "Este evento es para toda la famiilia y niños ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=8"
            ),
            EventData(
                "Favorito 2",
                "21-12-22",
                "12hs",
                "Titeres y comida tradicional argentina, case ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=3"
            )
        )
        val eventDataList = listOf<EventData>(
            EventData(
                "Evento.1",
                "21-12-22",
                "12hs",
                "Este evento es para toda la famiilia y niños ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=8"
            ),
            EventData(
                "Evento.2",
                "21-12-22",
                "12hs",
                "Titeres y comida tradicional argentina, case ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=3"
            ),
            EventData(
                "Evento.3",
                "21-12-22",
                "12hs",
                "Divertirte! Show gratuito de los Palmeras en ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=5"
            ),
            EventData(
                "Evento.4",
                "21-12-22",
                "12hs",
                "Cine al aire libre y gratuito, comidas y más ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=1"
            ),
            EventData(
                "Evento.5",
                "21-12-22",
                "12hs",
                "Feria artesanal, con show de malabares y una ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=7"
            ),
            EventData(
                "Evento.6",
                "21-12-22",
                "12hs",
                "Torneo de Voley, inscripción abierta, hasta  ... Leer más...",
                "Vte. López",
                "https://picsum.photos/150?random=9"
            )
        )
    }
}