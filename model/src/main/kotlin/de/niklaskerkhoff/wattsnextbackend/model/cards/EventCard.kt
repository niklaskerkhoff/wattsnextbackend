package de.niklaskerkhoff.wattsnextbackend.model.cards

class EventCard(
    name: String,
    description: String,
    image: String,
    val isCatastrophe: Boolean,
) : Card(name, description, image) {
}
