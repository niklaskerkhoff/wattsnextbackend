package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.energy.Technology
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection

sealed class ProgressCard(
    name: String,
    description: String,
    image: String,
    modifierCollection: ModifierCollection,
    val values: ProgressCardValues,
) : Card(name, description, image, modifierCollection) {

    class TechnologyCard(
        name: String,
        description: String,
        image: String,
        modifierCollection: ModifierCollection,
        values: ProgressCardValues,
        val technology: Technology
    ) : ProgressCard(
        name,
        description,
        image,
        modifierCollection,
        values
    )

    class ClimateCard(
        name: String,
        description: String,
        image: String,
        modifierCollection: ModifierCollection,
        values: ProgressCardValues,
    ) : ProgressCard(
        name,
        description,
        image,
        modifierCollection,
        values
    )
}
