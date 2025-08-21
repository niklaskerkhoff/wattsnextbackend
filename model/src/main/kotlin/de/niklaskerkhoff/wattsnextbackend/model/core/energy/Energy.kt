package de.niklaskerkhoff.wattsnextbackend.model.core.energy

data class Energy(
    val size: Int,
    val form: EnergyForm,
    val technology: Technology
)
