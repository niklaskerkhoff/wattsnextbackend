package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.app.actions.websockets.GameMessageSender
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
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

        if (gameInit.players.isEmpty()) {
            gameInitMap.remove(gameId)
        }

        gameMessageSender.sendGameState(gameId, gameInit)
        return removed
    }

    fun startGame(gameId: UUID) {
        val gameInit = getGameInitOrThrow(gameId)

        val (game, entityResolver) = GameFactory.buildGame(gameInit)
        val gameManager = GameManager(game, entityResolver, System.currentTimeMillis())
        gameManagerRepo.addGameManager(gameManager)

        gameMessageSender.sendGameState(gameId, GameData(game))
    }

    fun cancelGame(gameId: UUID, playerId: UUID): Boolean {
        val gameManager = gameManagerRepo.getGameManager(gameId)
        if (gameManager != null) {
            val player = gameManager.entityResolver.getPlayer(playerId)
            if (player != null) {
                gameManager.handleCancelGame(player.name)
                gameMessageSender.sendGameState(gameId, GameData(gameManager.game))
                return true
            } else {
                return false
            }
        } else {
            val gameInit = gameInitMap[gameId]
            if (gameInit != null) {
                val player = gameInit.getPlayer(playerId)
                if (player != null) {
                    gameInit.state = GameState.Cancelled
                    gameMessageSender.sendGameState(gameId, gameInit)
                    gameInitMap.remove(gameId)
                    return true
                } else {
                    return false
                }
            }
        }
        return false
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
