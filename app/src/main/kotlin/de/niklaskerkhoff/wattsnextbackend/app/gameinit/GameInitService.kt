package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.app.actions.websockets.GameMessageSender
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class GameInitService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val gameManagerRepo: GameManagerRepo,
    private val gameMessageSender: GameMessageSender,
) {
    private val gameInitMap: MutableMap<UUID, GameInit> = ConcurrentHashMap()

    fun createGame(request: CreateGameRequest): GameInitWithPlayerIdResponse {
        val gameInit = GameInit(request.gameMode, request.playerName)
        gameInitMap[gameInit.id] = gameInit
        return GameInitWithPlayerIdResponse(gameInit, gameInit.players.last().id)
    }

    fun joinGame(gameId: UUID, playerName: String): GameInitWithPlayerIdResponse {
        val gameInit = getGameInitOrThrow(gameId)
        gameInit.addPlayer(playerName)

        gameMessageSender.sendGameState(gameId, gameInit)
        return GameInitWithPlayerIdResponse(gameInit, gameInit.players.last().id)
    }

    fun leaveGameBeforeStart(gameId: UUID, playerId: UUID): Boolean {
        val gameInit = getGameInitOrThrow(gameId)
        val removed = gameInit.removePlayer(playerId)

        gameMessageSender.sendGameState(gameId, gameInit)
        return removed
    }

    fun startGame(gameId: UUID) {
        val gameInit = getGameInitOrThrow(gameId)

        val (game, entityResolver) = GameFactory.buildGame(gameInit)
        val gameManager = GameManager(game, entityResolver)
        gameManagerRepo.addGameManager(gameManager)

        gameMessageSender.sendGameState(gameId, GameData(game))
    }

    fun getGameState(gameId: UUID): Any {
        val game = gameManagerRepo.getGameManager(gameId)?.let { gameManager ->
            GameData(gameManager.game)
        }

        if (game != null) return game

        val gameInit = gameInitMap[gameId]
        if (gameInit != null) return gameInit

        throw IllegalArgumentException("Game not found")
    }

    private fun getGameInitOrThrow(gameId: UUID): GameInit {
        return gameInitMap[gameId] ?: throw IllegalArgumentException("Game not found")
    }
}
