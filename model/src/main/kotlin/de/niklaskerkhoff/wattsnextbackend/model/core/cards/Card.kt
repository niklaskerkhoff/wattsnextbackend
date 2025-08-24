package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import java.util.UUID

typealias CardEffect = ((Game) -> Pair<Game, List<CardEffectInformation>>)?

abstract class Card {
    abstract val id: UUID
    abstract val name: String
    abstract val description: String
    abstract val explanation: String
    abstract val imageSrc: String
    abstract val modifierCollection: ModifierCollection
    abstract val effect: CardEffect
    abstract val phase: Int

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
