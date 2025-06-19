package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.types.Technology

class ProgressCard(
    name: String,
    description: String,
    image: String,
    val values: ProgressCardValues,
    val technology: Technology,
) : Card(name, description, image)
