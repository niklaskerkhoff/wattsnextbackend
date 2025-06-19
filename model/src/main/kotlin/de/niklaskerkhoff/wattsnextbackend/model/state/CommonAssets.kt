package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType

class CommonAssets(
    var money: Int,
    var resources: Int,
    progressCards: List<ProgressCard>,
    eventCards: List<EventCard>
) {
    companion object {
        const val TECHNOLOGY_BOARD_HEIGHT = 3
        const val CLIMATE_CARD_COUNT = 10
    }

    private val progressCardDeck = progressCards.shuffled().toMutableList()
    private val catastropheEventCardDeck: MutableList<EventCard>
    private val standardEventCardDeck: MutableList<EventCard>

    private val _generationCards = List<MutableList<ProgressCard>>(TECHNOLOGY_BOARD_HEIGHT) { mutableListOf() }
    private val _distributionCards = List<MutableList<ProgressCard>>(TECHNOLOGY_BOARD_HEIGHT) { mutableListOf() }
    private val _storageCards = List<MutableList<ProgressCard>>(TECHNOLOGY_BOARD_HEIGHT) { mutableListOf() }
    private val _climateCards = List<MutableList<ProgressCard>>(CLIMATE_CARD_COUNT) { mutableListOf() }

    private var eventCard: EventCard? = null
    private var catastropheCard: EventCard? = null

    init {
        val (catastropheEventCardDeck, standardEventCardDeck) = eventCards.shuffled().partition { it.isCatastrophe }
        this.catastropheEventCardDeck = catastropheEventCardDeck.toMutableList()
        this.standardEventCardDeck = standardEventCardDeck.toMutableList()
    }

    val generationCards: List<List<ProgressCard>> = _generationCards
    val distributionCards: List<List<ProgressCard>> = _distributionCards
    val storageCards: List<List<ProgressCard>> = _storageCards
    val climateCards: List<List<ProgressCard>> = _climateCards

    fun drawProgressCardFromDeck() = progressCardDeck.removeLastOrNull()

    fun drawEventCardFromDeck() {
        eventCard = standardEventCardDeck.removeLast()
    }

    fun drawCatastropheCardFromDeck() {
        catastropheCard = catastropheEventCardDeck.removeLast()
    }

    fun playProgressCard(card: ProgressCard, position: Int) {
        if (position > TECHNOLOGY_BOARD_HEIGHT) {
            throw IllegalArgumentException("Position must be between 0 and $TECHNOLOGY_BOARD_HEIGHT, but was $position.")
        }

        getMutableProgressCardSectionCell(card.progressCardType, position).add(card)
    }

    fun getCurrentProgressCard(progressCardType: ProgressCardType, position: Int): ProgressCard? =
        getMutableProgressCardSectionCell(progressCardType, position).lastOrNull()

    private fun getMutableProgressCardSectionCell(
        progressCardType: ProgressCardType,
        position: Int
    ): MutableList<ProgressCard> {
        val list = getMutableProgressCardSection(progressCardType)
        if (position > list.size) {
            throw IllegalArgumentException("Position must be between 0 and ${list.size}, but was $position.")
        }
        return list[position]
    }

    private fun getMutableProgressCardSection(progressCardType: ProgressCardType) = when (progressCardType) {
        ProgressCardType.GENERATION -> _generationCards
        ProgressCardType.DISTRIBUTION -> _distributionCards
        ProgressCardType.STORAGE -> _storageCards
        ProgressCardType.CLIMATE -> _climateCards
    }
}
