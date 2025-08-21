package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.Game

abstract class Card {
    abstract val name: String
    abstract val description: String
    abstract val imageSrc: String
    abstract val modifierCollection: ModifierCollection
    abstract val effect: ((Game) -> Game)?

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "Card(name='$name')"
    }
}
