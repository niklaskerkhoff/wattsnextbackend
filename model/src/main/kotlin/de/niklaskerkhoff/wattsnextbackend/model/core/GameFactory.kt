package de.niklaskerkhoff.wattsnextbackend.model.core
/*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology

object GameFactory {
    fun createGame(
        playerNames: List<String>,
        progressCards: List<ProgressCard>,
        eventCards: List<EventCard>,
        initialMoney: Int,
        initialResources: Int,
        demandTargetsPerPhase: List<Map<Technology, Int>>,
        pointTargetsPerPhase: List<Int>,
        numberOfPhases: Int,
        numberOfMovesPerPhase: Int,
    ): Game {
        val progressCardDeck = progressCards.shuffled().toMutableList()
        val players = playerNames.map { Player(it, (1..5).map { progressCardDeck.removeLast() }) }
        val commonAssets = CommonAssetsFactory.createCommonAssets(
            progressCardDeck,
            eventCards,
            initialMoney,
            initialResources,
        )

        return Game(
            players,
            commonAssets,
            demandTargetsPerPhase,
            pointTargetsPerPhase,
            numberOfPhases,
            numberOfMovesPerPhase,
        )
    }


    object CommonAssetsFactory {
        const val TECHNOLOGY_BOARD_HEIGHT = 3
        const val CLIMATE_CARD_COUNT = 10

        fun createCommonAssets(
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
}
*/
