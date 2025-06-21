package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection

class EventCard(
    name: String,
    description: String,
    image: String,
    modifierCollection: ModifierCollection,
    val isCatastrophe: Boolean,
) : Card(name, description, image, modifierCollection) {
}
