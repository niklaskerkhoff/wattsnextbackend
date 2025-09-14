package de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification

import de.niklaskerkhoff.wattsnextbackend.model.core.TechnologyBoard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card

interface ModificationBase {
    val technologyBoard: TechnologyBoard

    fun provideModifiers(): List<Card>
}
