package de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
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

    /**
     * Returns the provider cards whose modifier actually changed the value, in application order.
     * Mirrors [modified] step by step so the caller can tell *why* a value differs from its base.
     */
    fun modifications(
        modifiedCard: ProgressCard,
        modificationBase: ModificationBase,
        parameter: P
    ): List<Card> {
        val providers = modificationBase.provideModifiers()
            .mapNotNull { card -> card.modifierCollection.getModifierConfig()?.let { card to it } }
            .sortedByDescending { (_, config) -> config.rank }
            .asReversed() // foldRight in modified() applies configs from the end

        val sources = mutableListOf<Card>()
        var acc = base
        for ((card, config) in providers) {
            val next = config.modify(acc, modifiedCard, parameter)
            if (next != acc) sources.add(card)
            acc = next
        }
        return sources
    }
}

fun <T> ModifiedValue<T, ModificationBase>.modified(
    modifiedCard: ProgressCard,
    modificationBase: ModificationBase,
) = modified(modifiedCard, modificationBase, modificationBase)

fun <T> ModifiedValue<T, ModificationBase>.modifications(
    modifiedCard: ProgressCard,
    modificationBase: ModificationBase,
) = modifications(modifiedCard, modificationBase, modificationBase)
