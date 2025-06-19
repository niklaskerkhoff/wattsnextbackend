package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType

class Game(
    progressCards: List<ProgressCard>,
    eventCards: List<EventCard>,
    private val catastropheCards: List<EventCard>,
    initialMoney: Int,
    initialResources: Int,
    private val demandTargetsPerPhase: List<Map<ProgressCardType, Int>>,
    private val pointTargetsPerPhase: List<Int>,
    private val numberOfPhases: Int,
    private val numberOfMovesPerPhase: Int,
) {
    private val _players: MutableList<Player> = mutableListOf()
    val players: List<Player> = _players

    val commonAssets = CommonAssets(initialMoney, initialResources, progressCards, eventCards)

    private var phase = 0
    private var moveInPhase = 0
    private var currentPlayer: Player? = null


    fun addPlayer(name: String) {
        val cards = (0..<5).map {
            commonAssets.drawProgressCardFromDeck() ?: throw IllegalStateException("No cards left in deck.")
        }
        _players.add(Player(name, cards))
    }
}
