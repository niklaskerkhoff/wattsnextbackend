package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

data class TargetableValue(
    val value: Int,
    val target: Int,
)

data class ModifiableValue<T>(
    val originalValue: T,
    val modifiedValue: T,
    val modifications: List<Modification> = emptyList()
)

sealed class Modification {
    abstract val type: String

    // Reserved for the dormant advanced-mode stacking mechanic; not emitted yet.
    // data class Stack(val multiplier: Int, override val type: String = "Stack") : Modification()
    data class Card(val name: String, override val type: String = "Card") : Modification()
}
