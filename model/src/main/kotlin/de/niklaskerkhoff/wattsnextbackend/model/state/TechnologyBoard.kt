package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType

typealias ProgressCardDeliveryStack = List<List<ProgressCard>>

data class TechnologyBoard(
    val generationCards: ProgressCardDeliveryStack,
    val distributionCards: ProgressCardDeliveryStack,
    val storageCards: ProgressCardDeliveryStack,
    val climateCards: ProgressCardDeliveryStack,
) {
    fun withProgressCardPlayed(progressCard: ProgressCard, position: Int): TechnologyBoard = copy(
        generationCards = generationCards.playIfRightTechnology(ProgressCardType.GENERATION, progressCard, position),
        distributionCards = distributionCards.playIfRightTechnology(
            ProgressCardType.DISTRIBUTION,
            progressCard,
            position
        ),
        storageCards = storageCards.playIfRightTechnology(ProgressCardType.STORAGE, progressCard, position),
        climateCards = climateCards.playIfRightTechnology(ProgressCardType.CLIMATE, progressCard, position),
    )

    fun getCurrentProgressCard(progressCardType: ProgressCardType, targetPosition: Int): ProgressCard? =
        getProgressCardDeliveryStack(progressCardType).let { stack ->
            if (targetPosition >= stack.size) throw IllegalArgumentException("Target position out of bounds.")
            stack[targetPosition].lastOrNull()
        }

    fun getAllCurrentProgressCards() =
        climateCards.map { it.lastOrNull() } +
                generationCards.map { it.lastOrNull() } +
                distributionCards.map { it.lastOrNull() } +
                storageCards.map { it.lastOrNull() }

    private fun ProgressCardDeliveryStack.playIfRightTechnology(
        progressCardType: ProgressCardType,
        card: ProgressCard,
        position: Int
    ): ProgressCardDeliveryStack =
        if (progressCardType == card.progressCardType)
            this.mapIndexed { index, cards -> if (index == position) cards + card else cards }
        else this

    private fun getProgressCardDeliveryStack(progressCardType: ProgressCardType): ProgressCardDeliveryStack =
        when (progressCardType) {
            ProgressCardType.GENERATION -> generationCards
            ProgressCardType.DISTRIBUTION -> distributionCards
            ProgressCardType.STORAGE -> storageCards
            ProgressCardType.CLIMATE -> climateCards
        }
}
