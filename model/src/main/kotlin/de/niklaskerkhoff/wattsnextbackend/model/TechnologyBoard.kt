package de.niklaskerkhoff.wattsnextbackend.model

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.Technology

class TechnologyBoard(private val height: Int) {

    private val _generationCards = MutableList<ProgressCard?>(height) { null }
    val generationCards: List<ProgressCard?> = _generationCards

    private val _distributionCards = MutableList<ProgressCard?>(height) { null }
    val distributionCards: List<ProgressCard?> = _distributionCards

    private val _storageCards = MutableList<ProgressCard?>(height) { null }
    val storageCards: List<ProgressCard?> = _storageCards

    fun playProgressCard(card: ProgressCard, position: Int) {
        if (position > height) {
            throw IllegalArgumentException("Position must be between 0 and $height, but was $position.")
        }

        getList(card.technology)[position] = card
    }

    fun getCard(technology: Technology, position: Int): ProgressCard? = getList(technology)[position]

    private fun getList(technology: Technology) = when (technology) {
        Technology.GENERATION -> _generationCards
        Technology.DISTRIBUTION -> _distributionCards
        Technology.STORAGE -> _storageCards
    }
}
