package de.niklaskerkhoff.wattsnextbackend.model.modifiers

/*
class ModifiedValue<T>(
    private val baseValue: T,
    private val modifierProv: ModifierProv
) {
    operator fun invoke() {
        return modifierProv.provide()
    }

    fun copy(baseValue: T) = ModifiedValue(baseValue, modifierProv)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModifiedValue<*>

        return baseValue == other.baseValue
    }

    override fun hashCode(): Int {
        return baseValue?.hashCode() ?: 0
    }
}
*/
