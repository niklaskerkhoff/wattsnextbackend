package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import java.util.*

data class GameData(
    val state: GameState,
    val money: Int,
    val resources: Int,
    val currentPlayerId: UUID,
    val players: List<PlayerData>,
    val board: BoardData,
    val phaseIndex: Int,
    val turnInPhase: Int,
    val turnsPerPhase: Int,
    val phases: List<PhaseData>,
    val progressCardPileSize: Int,
    val progressPoints: Int,
) {

    constructor(game: Game) : this(Result<Unit>(game))
    constructor(result: Result<*>) : this(
        state = result.game.state,
        money = result.game.money,
        resources = result.game.resources,
        currentPlayerId = result.game.currentPlayer.publicId,
        players = result.game.players.map { PlayerData(it, result) },
        board = BoardData(
            generationCards = result.game.technologyBoard.generationCards,
            distributionCards = result.game.technologyBoard.distributionCards,
            storageCards = result.game.technologyBoard.storageCards,
            climateActionCards = result.game.climateCards,
            eventCards = result.game.standardEventCards,
            catastropheCard = result.game.catastropheEventCard,
            result = result,
        ),
        phaseIndex = result.game.phase,
        turnInPhase = result.game.turnInPhase,
        turnsPerPhase = result.game.numberOfTurnsPerPhase,
        phases = (0..<result.game.numberOfPhases).map { PhaseData(result, it) },
        progressCardPileSize = result.game.progressCardDeck.size,
        progressPoints = result.game.calculateProgressPointInfo().progressPoints,
    )
}
