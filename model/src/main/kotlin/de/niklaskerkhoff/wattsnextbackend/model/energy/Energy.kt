package de.niklaskerkhoff.wattsnextbackend.model.energy

data class Energy(
    val size: Int,
    val form: EnergyForm,
    val technology: Technology
)