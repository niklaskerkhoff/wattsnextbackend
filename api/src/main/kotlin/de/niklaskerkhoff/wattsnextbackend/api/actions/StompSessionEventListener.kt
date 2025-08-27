package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.util.UUID

// TODO: put in adequate location

// Listen to connects and disconnects to keep PlayerSessionRegistry up-to-date
@Component
class StompSessionEventListener(
    private val playerSessionRegistry: PlayerSessionRegistry
) {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleConnect(event: SessionConnectEvent) {
        val headers = StompHeaderAccessor.wrap(event.message)
        val sessionId = headers.sessionId ?: return
        val playerId = headers.getFirstNativeHeader("playerId").toUUID() ?: return
        val gameId = headers.getFirstNativeHeader("gameId").toUUID() ?: return

        playerSessionRegistry.register(sessionId, gameId, playerId)
        log.info("Player $playerId connected to game $gameId (session $sessionId)")
    }

    @EventListener
    fun handleDisconnect(event: SessionDisconnectEvent) {
        val headers = StompHeaderAccessor.wrap(event.message)
        val sessionId = headers.sessionId ?: return

        playerSessionRegistry.unregister(sessionId)
        log.info("Session $sessionId disconnected")
    }

    private fun String?.toUUID() = if (this == null) null else UUID.fromString(this)
}

