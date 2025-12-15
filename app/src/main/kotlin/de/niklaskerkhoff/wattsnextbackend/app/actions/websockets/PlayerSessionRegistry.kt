package de.niklaskerkhoff.wattsnextbackend.app.actions.websockets

import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

// Maps session ids of connected clients to the according game and player id to identify clients when they send a
// message
@Component
class PlayerSessionRegistry {
    private val sessionMap = ConcurrentHashMap<String, PlayerSession>()

    data class PlayerSession(val gameId: UUID, val playerId: UUID)

    fun register(sessionId: String, gameId: UUID, playerId: UUID) {
        sessionMap[sessionId] = PlayerSession(gameId, playerId)
    }

    fun unregister(sessionId: String) {
        sessionMap.remove(sessionId)
    }

    fun get(sessionId: String): PlayerSession? = sessionMap[sessionId]
}
