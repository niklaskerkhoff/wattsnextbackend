package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import java.util.*

data class CreateGameRequest(
    val gameMode: GameMode,
    val playerName: String
) {
    enum class GameMode {
        START_WITH_NUCLEAR,
        START_WITH_COAL
    }
}

data class JoinGameRequest(
    val gameId: UUID,
    val playerName: String
)

