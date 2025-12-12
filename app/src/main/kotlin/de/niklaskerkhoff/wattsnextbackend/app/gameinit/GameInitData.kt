package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import java.util.*

data class CreateGameRequest(
    val gameMode: GameInit.Mode,
    val playerName: String
)

data class JoinGameRequest(
    val gameId: UUID,
    val playerName: String
)

data class LeaveGameRequest(
    val gameId: UUID,
    val playerId: UUID
)

data class StartGameRequest(
    val gameId: UUID,
)

data class CancelGameRequest(
    val gameId: UUID,
    val playerId: UUID
)

data class GameInitWithPlayerIdResponse(
    val game: GameInit,
    val playerId: UUID,
)
