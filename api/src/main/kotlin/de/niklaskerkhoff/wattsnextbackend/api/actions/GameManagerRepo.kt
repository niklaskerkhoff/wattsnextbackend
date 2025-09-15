package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class GameManagerRepo {
    private val gameManagers: MutableMap<UUID, GameManager> = ConcurrentHashMap()

    fun getGameManager(gameId: UUID): GameManager? {
        return gameManagers[gameId]
    }

    fun addGameManager(gameManager: GameManager) {
        gameManagers[gameManager.game.publicId] = gameManager
    }

    fun removeGameManager(gameId: UUID) {
        gameManagers.remove(gameId)
    }
}
