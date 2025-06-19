package de.niklaskerkhoff.wattsnextbackend.model

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard

class CommonAssets(
    var money: Int,
    var resources: Int,
) {
    companion object {
        const val TECHNOLOGY_BOARD_HEIGHT = 3
        const val CLIMATE_CARD_COUNT = 10
        const val EVENT_CARD_COUNT = 2
    }

    val technologyBoard = TechnologyBoard(TECHNOLOGY_BOARD_HEIGHT)
    val climateCards = MutableList<ProgressCard?>(CLIMATE_CARD_COUNT) { null }
    val eventCards = MutableList<EventCard?>(EVENT_CARD_COUNT) { null }
}
