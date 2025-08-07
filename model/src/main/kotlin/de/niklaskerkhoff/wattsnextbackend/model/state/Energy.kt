package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.types.EnergyType

data class Energy(
    val value: Int,
    val type: EnergyType
)