package de.niklaskerkhoff.wattsnextbackend.model.modifiers

import de.niklaskerkhoff.wattsnextbackend.model.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.state.Game

interface ModifierProvider<T> {
    fun provide(modifyingCard: Card): UnifiedModifier<T>?
}

typealias SimpleModifierExecutorFunction<T> =
            (modifyingCard: Card, modifiedCard: ProgressCard, acc: T, game: Game) -> T

class SimpleModifierProvider<T>(
    val getModifier: ModifierCollection.() -> Modifier<SimpleModifierExecutorFunction<T>>?,
    val modifiedProgressCard: ProgressCard,
    val game: Game,
) : ModifierProvider<T> {
    override fun provide(modifyingCard: Card) = modifiedProgressCard.modifierCollection.getModifier()?.let {
        UnifiedModifier(it.rank) { acc: T ->
            it.modify(
                modifyingCard,
                modifiedProgressCard,
                acc,
                game,
            )
        }
    }
}

typealias WithTargetPositionModifierFunction<T> =
            (modifyingCard: Card, modifiedCard: ProgressCard, acc: T, game: Game, targetPosition: Int) -> T

class WithTargetPositionModifierProvider<T>(
    val getModifier: ModifierCollection.() -> Modifier<WithTargetPositionModifierFunction<T>>?,
    val modifiedProgressCard: ProgressCard,
    val game: Game,
    val targetPosition: Int,
) : ModifierProvider<T> {
    override fun provide(modifyingCard: Card) = modifiedProgressCard.modifierCollection.getModifier()?.let {
        UnifiedModifier(it.rank) { acc: T ->
            it.modify(
                modifyingCard,
                modifiedProgressCard,
                acc,
                game,
                targetPosition
            )
        }
    }
}
