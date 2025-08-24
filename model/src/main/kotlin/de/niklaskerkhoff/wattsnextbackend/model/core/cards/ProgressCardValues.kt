package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply

data class ProgressCardValues(
    val basePoints: Int,
    val systemPoints: Int,
    val supplyRequirementsForSystem: List<Supply>,
    val moneyCosts: Int,
    val resourceCosts: Int,
)
