package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

// TODO: Put in adequate location

@Component
class GameMessageSender(
    private val messagingTemplate: SimpMessagingTemplate
) {

    private fun sendToGame(gameId: String, suffix: String?, payload: Any) {
        val destination = if (suffix.isNullOrBlank()) {
            "/topic/game/$gameId"
        } else {
            "/topic/game/$gameId/$suffix"
        }
        messagingTemplate.convertAndSend(destination, payload)
    }

    fun sendGameState(gameId: String, gameState: GameDto) {
        sendToGame(gameId, null, gameState)
    }

    // TODO: specific result
    fun sendEarnMoneyResult(gameId: String, result: ActionResponse<String>) {
        sendToGame(gameId, "earnMoneyResult", result)
    }

    // TODO: specific result
    fun sendPlayCardIntentResult(gameId: String, result: ActionResponse<String>) {
        sendToGame(gameId, "playCardIntentResult", result)
    }

    // TODO: specific result
    fun sendPlayCardResult(gameId: String, result: ActionResponse<String>) {
        sendToGame(gameId, "playCardResult", result)
    }
}
