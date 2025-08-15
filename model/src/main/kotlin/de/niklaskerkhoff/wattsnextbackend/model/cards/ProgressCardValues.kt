package de.niklaskerkhoff.wattsnextbackend.model.cards

import de.niklaskerkhoff.wattsnextbackend.model.energy.Energy

data class ProgressCardValues(
    val basePoints: Int,
    val systemPoints: Int,
    val energyRequirementsForSystem: List<Energy>,
    val energyOutput: Energy,
    val moneyCosts: Int,
    val resourceCosts: Int,
)
