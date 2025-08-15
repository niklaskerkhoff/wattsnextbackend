package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import java.util.UUID

open class Card(
    val name: String,
    val description: String,
    val imageSrc: String,
    val modifierCollection: ModifierCollection
)
