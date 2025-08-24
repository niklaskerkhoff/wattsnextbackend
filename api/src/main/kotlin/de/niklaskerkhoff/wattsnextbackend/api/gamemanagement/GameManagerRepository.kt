package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManager
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Component
class GameManagerRepository {
    private val gameManagers: MutableMap<UUID, GameManager> = ConcurrentHashMap()

    fun getGameManager(gameId: UUID): GameManager? {
        return gameManagers[gameId]
    }

    fun addGameManager(gameId: UUID, gameManager: GameManager) {
        gameManagers[gameId] = gameManager
    }

    fun removeGameManager(gameId: UUID) {
        gameManagers.remove(gameId)
    }
}
