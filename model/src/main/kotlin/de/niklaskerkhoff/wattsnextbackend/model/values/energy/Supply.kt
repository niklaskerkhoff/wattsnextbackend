package de.niklaskerkhoff.wattsnextbackend.model.values.energy

sealed class Supply {
    abstract val type: String
    abstract val fulfilled: Boolean?

    data class Energy(
        val technology: Technology,
        val form: EnergyForm,
        val size: Int,
        override val fulfilled: Boolean? = null
    ) : Supply() {
        override val type: String = "energy"
    }

    data class Achievement(
        val name: String, // can be CarbonCapture, NuclearWasteRepository or ChemicalEnergy
        override val fulfilled: Boolean? = null
    ) : Supply() {
        override val type: String = "achievement"
    }

    object Never : Supply() {
        override val type: String = "never"
        override val fulfilled: Boolean = false
    }
/*
    object Always : Supply() {
        override val type: String = "always"
        override val fulfilled: Boolean = true
    }*/
}
