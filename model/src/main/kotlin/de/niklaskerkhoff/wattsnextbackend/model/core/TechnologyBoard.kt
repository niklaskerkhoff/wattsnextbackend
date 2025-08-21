package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology

typealias TechnologyColumn = List<List<ProgressCard.TechnologyCard>>

data class TechnologyBoard(
    val generationCards: TechnologyColumn,
    val distributionCards: TechnologyColumn,
    val storageCards: TechnologyColumn,
) {
    fun withProgressCardPlayed(progressCard: ProgressCard, position: Int): TechnologyBoard = copy(
        generationCards = generationCards.playIfRightTechnology(Technology.GENERATION, progressCard, position),
        distributionCards = distributionCards.playIfRightTechnology(
            Technology.DISTRIBUTION,
            progressCard,
            position
        ),
        storageCards = storageCards.playIfRightTechnology(Technology.STORAGE, progressCard, position),
    )

    fun getCurrentTechnologyCard(technology: Technology, targetPosition: Int): ProgressCard.TechnologyCard? =
        getTechnologyColumn(technology).let { column ->
            require(0 <= targetPosition && targetPosition < column.size) {
                "targetPosition out of bounds: $targetPosition"
            }
            column[targetPosition].lastOrNull()
        }

    fun getAllCurrentProgressCards(): List<ProgressCard.TechnologyCard?> =
        generationCards.map { it.lastOrNull() } +
                distributionCards.map { it.lastOrNull() } +
                storageCards.map { it.lastOrNull() }

    private fun TechnologyColumn.playIfRightTechnology(
        technology: Technology,
        card: ProgressCard,
        position: Int
    ): TechnologyColumn =
        if (technology == (card as ProgressCard.TechnologyCard).technology)
            this.mapIndexed { index, cards -> if (index == position) cards + card else cards }
        else this

    private fun getTechnologyColumn(technology: Technology): TechnologyColumn =
        when (technology) {
            Technology.GENERATION -> generationCards
            Technology.DISTRIBUTION -> distributionCards
            Technology.STORAGE -> storageCards
        }
}
