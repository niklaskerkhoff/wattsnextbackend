package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.energy.Technology

typealias ProgressCardDeliveryStack = List<List<ProgressCard>>

data class TechnologyBoard(
    val generationCards: ProgressCardDeliveryStack,
    val distributionCards: ProgressCardDeliveryStack,
    val storageCards: ProgressCardDeliveryStack,
    val climateCards: ProgressCardDeliveryStack,
) {
    fun withProgressCardPlayed(progressCard: ProgressCard, position: Int): TechnologyBoard = copy(
        generationCards = generationCards.playIfRightTechnology(Technology.GENERATION, progressCard, position),
        distributionCards = distributionCards.playIfRightTechnology(
            Technology.DISTRIBUTION,
            progressCard,
            position
        ),
        storageCards = storageCards.playIfRightTechnology(Technology.STORAGE, progressCard, position),
        climateCards = climateCards.playIfRightTechnology(Technology.CLIMATE, progressCard, position),
    )

    fun getCurrentTechonologyProgressCard(technology: Technology, targetPosition: Int): ProgressCard.TechnologyCard? =
        getProgressCardDeliveryStack(technology).let { stack ->
            if (targetPosition >= stack.size) throw IllegalArgumentException("Target position out of bounds.")
            stack[targetPosition].lastOrNull() as ProgressCard.TechnologyCard
        }

    fun getAllCurrentProgressCards() =
        climateCards.map { it.lastOrNull() } +
                generationCards.map { it.lastOrNull() } +
                distributionCards.map { it.lastOrNull() } +
                storageCards.map { it.lastOrNull() }

    private fun ProgressCardDeliveryStack.playIfRightTechnology(
        technology: Technology,
        card: ProgressCard,
        position: Int
    ): ProgressCardDeliveryStack =
        if (technology == (card as ProgressCard.TechnologyCard).technology)
            this.mapIndexed { index, cards -> if (index == position) cards + card else cards }
        else this

    private fun getProgressCardDeliveryStack(technology: Technology): ProgressCardDeliveryStack =
        when (technology) {
            Technology.GENERATION -> generationCards
            Technology.DISTRIBUTION -> distributionCards
            Technology.STORAGE -> storageCards
            Technology.CLIMATE -> climateCards
        }
}
