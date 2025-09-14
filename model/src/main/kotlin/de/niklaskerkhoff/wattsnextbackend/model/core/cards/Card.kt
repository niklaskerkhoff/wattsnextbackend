package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModificationBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import java.util.*

typealias CardEffect = ((Game) -> Pair<Game, List<CardEffectInformation>>)
typealias SimpleModifiedValue<T> = ModifiedValue<T, ModificationBase>
typealias WithIntModifiedValue<T> = ModifiedValue<T, Pair<ModificationBase, Int>>

abstract class Card {
    abstract val modifierCollection: ModifierCollection
    abstract val id: UUID
    abstract val name: String
    abstract val effect: CardEffect
    abstract val phaseIndex: Int

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Card(id=$id, name='$name')"
    }
}
