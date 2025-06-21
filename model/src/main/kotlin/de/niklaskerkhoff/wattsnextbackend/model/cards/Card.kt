package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection

open class Card(
    val name: String,
    val description: String,
    val image: String,
    val modifierCollection: ModifierCollection
)
