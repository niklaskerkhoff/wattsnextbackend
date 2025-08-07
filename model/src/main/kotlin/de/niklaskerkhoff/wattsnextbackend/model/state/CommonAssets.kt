package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType


data class CommonAssets(
    val progressCardDeck: List<ProgressCard>,
    val standardEventCardDeck: List<EventCard>,
    val catastropheEventCardDeck: List<EventCard>,

    val money: Int,
    val resources: Int,

    val technologyBoard: TechnologyBoard,


    val standardEventCard: EventCard? = null,
    val catastropheEventCard: EventCard? = null,
) {
    companion object {
        const val TECHNOLOGY_BOARD_HEIGHT = 3
        const val CLIMATE_CARD_COUNT = 10

        fun initialize(
            progressCardDeck: List<ProgressCard>,
            eventCards: List<EventCard>,
            initialMoney: Int,
            initialResources: Int,
        ): CommonAssets {
            val (catastropheEventCardDeck, standardEventCardDeck) = eventCards.shuffled().partition { it.isCatastrophe }
            return CommonAssets(
                progressCardDeck,
                standardEventCardDeck,
                catastropheEventCardDeck,

                initialMoney,
                initialResources,

                TechnologyBoard(
                    List(TECHNOLOGY_BOARD_HEIGHT) { emptyList() },
                    List(TECHNOLOGY_BOARD_HEIGHT) { emptyList() },
                    List(TECHNOLOGY_BOARD_HEIGHT) { emptyList() },
                    List(CLIMATE_CARD_COUNT) { emptyList() },
                )
            )
        }
    }

    fun getAllCards() =
        technologyBoard.getAllCurrentProgressCards() + standardEventCard + catastropheEventCard
}
