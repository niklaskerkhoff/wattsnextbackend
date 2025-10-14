package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.api.actions.websockets.GameMessageSender
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
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

    fun startGame(gameId: UUID) {
        val gameInit = getGameInitOrThrow(gameId)

        val (game, entityResolver) = GameFactory.buildGame(gameInit)
        val gameManager = GameManager(game, entityResolver)
        gameManagerRepo.addGameManager(gameManager)

        gameMessageSender.sendGameState(gameId, GameData(game))
    }

    // TODO: It seems like there is still a gameInit object when the game has already started, so check for a game object
    // first
    fun getGameState(gameId: UUID): Any {
        /*val gameInit = gameInitMap[gameId]
        if (gameInit != null) return gameInit

        val game = gameManagerRepo.getGameManager(gameId)?.let { gameManager ->
            GameData(gameManager.game)
        }

        return game ?: throw IllegalArgumentException("Game not found")*/

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
