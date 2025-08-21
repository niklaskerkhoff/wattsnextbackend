package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.api.actions.PlayerDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class GameManagementService(private val messagingTemplate: SimpMessagingTemplate
) {
    // TODO: Where to manage the open games?
    private val games: MutableMap<String, GameManager> = ConcurrentHashMap()

    fun createGame(request: CreateGameRequest): CreateGameResponse {
        val gameId = UUID.randomUUID().toString()
        // TODO: Initialize new game and add to map
        val player = joinGame(gameId, request.playerName)
        return CreateGameResponse(gameId, player)
    }

    fun joinGame(gameId: String, playerName: String): PlayerDto {
        val game = games[gameId] ?: throw IllegalArgumentException("Game not found")
        val playerId = UUID.randomUUID()
        // TODO: Create new player and add to game
        val player = PlayerDto(playerId, playerName, emptyList())
        messagingTemplate.convertAndSend("/topic/game/$gameId", game)
        return player
    }

    fun startGame(gameId: String) {
        val game = games[gameId] ?: throw IllegalArgumentException("Game not found")
        // TODO: Set game state to running
        messagingTemplate.convertAndSend("/topic/game/$gameId", game)
    }
}