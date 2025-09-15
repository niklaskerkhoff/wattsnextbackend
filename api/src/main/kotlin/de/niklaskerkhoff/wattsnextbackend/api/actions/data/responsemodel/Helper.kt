package de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel

data class TargetableValue(
    val value: Int,
    val target: Int,
)

data class ModifiableValue<T>(
    val originalValue: T,
    val modifiedValue: T, // TODO: Optional or not?
    val modifications: List<Modification> = emptyList()
)

sealed class Modification {
    abstract val type: String

    data class Stack(val multiplier: Int, override val type: String = "Stack") : Modification()
    data class Card(val name: String, override val type: String = "Card") : Modification()
}
