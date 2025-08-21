package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Energy

data class ProgressCardValues(
    val basePoints: Int,
    val systemPoints: Int,
    val energyRequirementsForSystem: List<Energy>,
    val energyOutput: Energy,
    val moneyCosts: Int,
    val resourceCosts: Int,
)
