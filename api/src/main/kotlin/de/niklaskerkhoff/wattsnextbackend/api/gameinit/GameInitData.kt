package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import java.util.*

data class CreateGameRequest(
    val gameMode: GameInit.Mode,
    val playerName: String
)

data class JoinGameRequest(
    val gameId: UUID,
    val playerName: String
)

data class StartGameRequest(
    val gameId: UUID,
)

data class CreateGameResponse(
    val gameId: UUID,
    val playerName: String
)

