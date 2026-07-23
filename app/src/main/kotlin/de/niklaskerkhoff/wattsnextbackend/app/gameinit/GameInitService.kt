package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.app.actions.websockets.GameMessageSender
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
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
        val gameInit = GameInit(request.gameMode, request.playerName, generateUniqueShareCode())
        gameInitMap[gameInit.id] = gameInit
        return GameInitWithPlayerIdResponse(gameInit, gameInit.players.last().id)
    }

    fun joinGame(shareCode: String, playerName: String): GameInitWithPlayerIdResponse {
        val normalizedShareCode = shareCode.trim().uppercase()
        val gameInit = gameInitMap.values.find { it.shareCode == normalizedShareCode }
            ?: throw IllegalArgumentException("Game not found")

        // Enforce the player limit and add the player atomically so concurrent joins can't
        // slip past the check and overfill the lobby. A CONFLICT (409) lets the frontend tell
        // "lobby full" apart from other join failures.
        synchronized(gameInit) {
            if (gameInit.players.size >= MAX_PLAYERS) {
                throw ResponseStatusException(HttpStatus.CONFLICT, "Lobby is full (max $MAX_PLAYERS players)")
            }
            gameInit.addPlayer(playerName)
        }

        gameMessageSender.sendGameState(gameInit.id, gameInit)
        return GameInitWithPlayerIdResponse(gameInit, gameInit.players.last().id)
    }

    private fun generateUniqueShareCode(): String {
        while (true) {
            val code = (1..SHARE_CODE_LENGTH).map { SHARE_CODE_ALPHABET.random() }.joinToString("")
            if (gameInitMap.values.none { it.shareCode == code }) return code
        }
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
        // Idempotent: once the game is running, ignore further start requests so the running
        // game is never rebuilt/reset.
        if (gameManagerRepo.getGameManager(gameId) != null) return

        val gameInit = getGameInitOrThrow(gameId)

        val (game, entityResolver) = GameFactory.buildGame(gameInit)
        val gameManager = GameManager(game, entityResolver, System.currentTimeMillis())
        gameManagerRepo.addGameManager(gameManager)
        gameInitMap.remove(gameId)

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

    companion object {
        private const val MAX_PLAYERS = 6

        private const val SHARE_CODE_LENGTH = 4

        // Alphabet without easily confused characters (no 0/O, 1/I).
        private const val SHARE_CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
    }
}
