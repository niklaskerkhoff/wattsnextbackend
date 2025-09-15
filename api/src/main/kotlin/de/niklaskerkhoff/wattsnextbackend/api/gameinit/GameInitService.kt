package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManagerRepo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class GameInitService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val gameManagerRepo: GameManagerRepo,
) {
    private val gameInitMap: MutableMap<UUID, GameInit> = ConcurrentHashMap()

    fun createGame(request: CreateGameRequest): CreateGameResponse {
        val gameInit = GameInit(request.gameMode, request.playerName)
        gameInitMap[gameInit.id] = gameInit
        return CreateGameResponse(gameInit.id, request.playerName)
    }

    fun joinGame(gameId: UUID, playerName: String): String {
        val gameInit = getGameOrThrow(gameId)
        gameInit.playerNames.add(playerName)

        messagingTemplate.convertAndSend("/topic/game/$gameId", gameInit)
        return playerName
    }

    fun startGame(gameId: UUID) {
        val gameInit = getGameOrThrow(gameId)

        val (game, entityResolver) = GameBuilder.buildGame(gameInit)
        val gameManager = GameManager(game, entityResolver)
        gameManagerRepo.addGameManager(gameManager)

        messagingTemplate.convertAndSend("/topic/game/$gameId", game)
    }

    private fun getGameOrThrow(gameId: UUID): GameInit {
        return gameInitMap[gameId] ?: throw IllegalArgumentException("Game not found")
    }
}
