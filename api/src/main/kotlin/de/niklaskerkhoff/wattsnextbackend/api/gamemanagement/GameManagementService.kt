package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.api.actions.PlayerDto
import de.niklaskerkhoff.wattsnextbackend.model.core.GameFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameManagementService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val gameManagerRepository: GameManagerRepository,
) {
    // TODO: Where to manage the open games?

    fun createGame(request: CreateGameRequest): CreateGameResponse {
        val gameId = UUID.randomUUID()
        // TODO: Initialize new game and add to map
        val player = joinGame(gameId, request.playerName)
        return CreateGameResponse(gameId, player)
    }

    fun joinGame(gameId: UUID, playerName: String): PlayerDto {
        val gameManager = getGameManagerOrThrow(gameId)
        val playerId = UUID.randomUUID()
        // TODO: Create new player and add to game
        val player = PlayerDto(playerId, playerName, emptyList())
        messagingTemplate.convertAndSend("/topic/game/$gameId", gameManager.getStateInfo())
        return player
    }

    fun startGame(gameId: UUID) {
        val game = getGameManagerOrThrow(gameId)
        // TODO: Set game state to running
        messagingTemplate.convertAndSend("/topic/game/$gameId", game)
    }

    private fun getGameManagerOrThrow(gameId: UUID): GameManager {
        return gameManagerRepository.getGameManager(gameId) ?: throw IllegalArgumentException("Game not found")
    }
}
