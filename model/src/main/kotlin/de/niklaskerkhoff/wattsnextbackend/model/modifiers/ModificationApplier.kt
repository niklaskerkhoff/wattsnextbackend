package de.niklaskerkhoff.wattsnextbackend.model.modifiers

import de.niklaskerkhoff.wattsnextbackend.model.state.Game

class ModificationApplier<T>(
    private val modifierProvider: ModifierProvider<T>,
    private val initialValue: T,
    private val game: Game,
) {
    fun applyModification(): T =
        game.commonAssets
            .run {
                climateCards.map { it.lastOrNull() } +
                        generationCards.map { it.lastOrNull() } +
                        distributionCards.map { it.lastOrNull() } +
                        storageCards.map { it.lastOrNull() } +
                        standardEventCard +
                        catastropheEventCard
            }
            .mapNotNull { modifyingCard ->
                modifyingCard?.let { modifierProvider.provide(it) }
            }
            .sortedByDescending { modifier -> modifier.rank }
            .foldRight(initialValue) { modifier, acc ->
                modifier.modify(acc)
            }
}