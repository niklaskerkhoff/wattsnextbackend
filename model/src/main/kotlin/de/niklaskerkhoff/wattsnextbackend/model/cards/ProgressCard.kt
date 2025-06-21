package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType

class ProgressCard(
    name: String,
    description: String,
    image: String,
    modifierCollection: ModifierCollection,
    val values: ProgressCardValues,
    val progressCardType: ProgressCardType,
) : Card(name, description, image, modifierCollection)
