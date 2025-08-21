package de.niklaskerkhoff.wattsnextbackend.model.modifiers

import de.niklaskerkhoff.wattsnextbackend.model.core.Game

class ModificationApplier<T>(
    private val modifierProvider: ModifierProvider<T>,
    private val initialValue: T,
    private val game: Game,
) {
    fun applyModification(): T =
        game.commonAssets.getAllCards()
            .mapNotNull { modifyingCard ->
                modifyingCard?.let { modifierProvider.provide(it) }
            }
            .sortedByDescending { modifier -> modifier.rank }
            .foldRight(initialValue) { modifier, acc ->
                modifier.modify(acc)
            }
}
