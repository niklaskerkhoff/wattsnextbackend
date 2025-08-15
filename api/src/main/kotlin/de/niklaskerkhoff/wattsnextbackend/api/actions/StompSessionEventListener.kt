package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

// TODO: put in adequate location

@Component
class StompSessionEventListener(
    private val playerSessionRegistry: PlayerSessionRegistry
) {

    @EventListener
    fun handleConnect(event: SessionConnectEvent) {
        val headers = StompHeaderAccessor.wrap(event.message)
        val sessionId = headers.sessionId ?: return
        val playerId = headers.getFirstNativeHeader("playerId") ?: return
        val gameId = headers.getFirstNativeHeader("gameId") ?: return

        playerSessionRegistry.register(sessionId, gameId, playerId)
        println("Player $playerId connected to game $gameId (session $sessionId)")
    }

    @EventListener
    fun handleDisconnect(event: SessionDisconnectEvent) {
        val headers = StompHeaderAccessor.wrap(event.message)
        val sessionId = headers.sessionId ?: return

        playerSessionRegistry.unregister(sessionId)
        println("Session $sessionId disconnected")
    }
}

