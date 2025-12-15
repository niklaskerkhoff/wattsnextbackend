package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology

typealias TechnologyColumn = List<List<ProgressCard.TechnologyCard>>

data class TechnologyBoard(
    val generationCards: TechnologyColumn,
    val distributionCards: TechnologyColumn,
    val storageCards: TechnologyColumn,
) {
    init {
        require(generationCards.size == distributionCards.size)
        require(generationCards.size == storageCards.size)
    }

    fun withCardPlayed(card: ProgressCard.TechnologyCard, position: Int): TechnologyBoard = copy(
        generationCards = generationCards.playIfRightTechnology(Technology.Generation, card, position),
        distributionCards = distributionCards.playIfRightTechnology(
            Technology.Distribution,
            card,
            position
        ),
        storageCards = storageCards.playIfRightTechnology(Technology.Storage, card, position),
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

    fun getColumnSize() = generationCards.size

    fun getSameTechnologyCardStack(technology: Technology, targetPosition: Int): List<ProgressCard.TechnologyCard> =
        getTechnologyColumn(technology).let { column ->
            require(0 <= targetPosition && targetPosition < column.size) {
                "targetPosition out of bounds: $targetPosition"
            }
            val stack = column[targetPosition]
            if (stack.isEmpty()) {
                emptyList()
            } else {
                val name = stack.last().name
                stack.takeLastWhile { it.name == name }
            }
        }

    fun getPositionOf(card: ProgressCard.TechnologyCard) =
        getTechnologyColumn(card.technology).indexOfFirst(
            { it.lastOrNull() == card }
        )

    private fun TechnologyColumn.playIfRightTechnology(
        technology: Technology,
        card: ProgressCard.TechnologyCard,
        position: Int
    ): TechnologyColumn =
        if (technology == card.supply.base.technology)
            this.mapIndexed { index, cards -> if (index == position) cards + card else cards }
        else this

    private fun getTechnologyColumn(technology: Technology): TechnologyColumn =
        when (technology) {
            Technology.Generation -> generationCards
            Technology.Distribution -> distributionCards
            Technology.Storage -> storageCards
        }
}
