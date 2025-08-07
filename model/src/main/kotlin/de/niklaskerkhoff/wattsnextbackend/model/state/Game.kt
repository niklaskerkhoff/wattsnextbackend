package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType

data class Game(
    val players: List<Player>,
    val commonAssets: CommonAssets,
    private val demandTargetsPerPhase: List<Map<ProgressCardType, Int>>,
    private val pointTargetsPerPhase: List<Int>,
    private val numberOfPhases: Int,
    private val numberOfMovesPerPhase: Int,
) {
    val phase = 0
    val moveInPhase = 0
    private val totalMove = moveInPhase * phase

    val currentPlayer get() = players[totalMove % players.size]



    companion object {
        fun initialize(
            playerNames: List<String>,
            progressCards: List<ProgressCard>,
            eventCards: List<EventCard>,
            initialMoney: Int,
            initialResources: Int,
            demandTargetsPerPhase: List<Map<ProgressCardType, Int>>,
            pointTargetsPerPhase: List<Int>,
            numberOfPhases: Int,
            numberOfMovesPerPhase: Int,
        ): Game {
            val progressCardDeck = progressCards.shuffled().toMutableList()
            val players = playerNames.map { Player(it, (1..5).map { progressCardDeck.removeLast() }) }
            val commonAssets = CommonAssets.initialize(
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
    }
}
