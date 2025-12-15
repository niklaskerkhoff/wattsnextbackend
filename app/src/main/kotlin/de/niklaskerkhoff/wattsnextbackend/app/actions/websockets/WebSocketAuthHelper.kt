package de.niklaskerkhoff.wattsnextbackend.app.actions.websockets

import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component

@Component
class WebSocketAuthHelper(
    private val playerSessionRegistry: PlayerSessionRegistry
) {
    fun getAndValidateSessionInfo(headerAccessor: StompHeaderAccessor): PlayerSessionRegistry.PlayerSession {
        val sessionId = headerAccessor.sessionId ?: throw IllegalStateException("No session")
        val sessionInfo = playerSessionRegistry.get(sessionId) ?: throw IllegalStateException("Player not registered")

        return sessionInfo
    }
}
