package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.PlayerDto
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
    val gameId: String,
    val playerName: String
)

data class StartGameRequest(
    val gameId: String,
)

data class CreateGameResponse(
    val gameId: String,
    val player: PlayerDto
)

