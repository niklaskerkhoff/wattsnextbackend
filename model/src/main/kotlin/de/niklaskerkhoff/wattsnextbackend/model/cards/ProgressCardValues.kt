package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.Energy

data class ProgressCardValues(
    val basePoints: Int,
    val advancedPoints: Int,
    val energyForAdvancement: List<Energy>,
    val energyOutput: Energy,
    val price: Int
)
