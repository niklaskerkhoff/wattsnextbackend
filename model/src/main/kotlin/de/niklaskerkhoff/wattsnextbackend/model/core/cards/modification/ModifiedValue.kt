package de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard

typealias ModifierFunction<T, P> = (acc: T, modifiedSubject: ProgressCard, parameter: P) -> T

class ModifiedValue<out T, P>(
    val base: T,
    private val getModifierConfig: ModifierCollection.() -> ModifierConfig<ModifierFunction<T, P>>?,
) {

    fun modified(
        modifiedCard: ProgressCard,
        modificationBase: ModificationBase,
        parameter: P
    ): T =
        modificationBase.provideModifiers()
            .mapNotNull { card -> card.modifierCollection.getModifierConfig() }
            .sortedByDescending { config -> config.rank }
            .foldRight(base) { config, acc ->
                config.modify(acc, modifiedCard, parameter)
            }
}

fun <T> ModifiedValue<T, ModificationBase>.modified(
    modifiedCard: ProgressCard,
    modificationBase: ModificationBase,
) = modified(modifiedCard, modificationBase, modificationBase)
