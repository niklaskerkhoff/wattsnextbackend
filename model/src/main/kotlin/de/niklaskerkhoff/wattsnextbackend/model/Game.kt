package de.niklaskerkhoff.wattsnextbackend.model

import de.niklaskerkhoff.wattsnextbackend.model.types.Technology

class Game(
    private val demandTargetsPerPhase: List<Map<Technology, Int>>,
    private val pointTargetsPerPhase: List<Int>,
    private val numberOfPhases: Int,
    private val numberOfMovesPerPhase: Int,
    initialMoney: Int,
    initialResources: Int,
) {

    private val _players: MutableList<Player> = mutableListOf()
    val players: List<Player> = _players

    val commonAssets = CommonAssets(initialMoney, initialResources)

    private var phase = 0
    private var moveInPhase = 0
}
