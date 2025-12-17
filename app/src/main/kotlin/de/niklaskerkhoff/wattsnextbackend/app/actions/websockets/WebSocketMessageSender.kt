package de.niklaskerkhoff.wattsnextbackend.app.actions.websockets

import de.niklaskerkhoff.wattsnextbackend.app.actions.data.ActionResponse
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.app.gameinit.GameInit
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameMessageSender(
    private val messagingTemplate: SimpMessagingTemplate
) {

    private fun sendToGame(gameId: UUID, suffix: String?, payload: Any) {
        val destination = "/topic/game/$gameId" + (if (suffix.isNullOrBlank()) "" else "/$suffix")
        messagingTemplate.convertAndSend(destination, payload)
    }

    fun sendGameState(gameId: UUID, gameState: GameData) {
        sendToGame(gameId, null, gameState)
    }

    fun sendGameState(gameId: UUID, gameState: GameInit) {
        sendToGame(gameId, null, gameState)
    }

    fun sendRollDiceResponse(gameId: UUID, result: ActionResponse) {
        sendToGame(gameId, "earnMoneyResult", result)
    }

    fun sendPlayClimateCardResponse(gameId: UUID, result: ActionResponse) {
        sendToGame(gameId, "playClimateCardResult", result)
    }

    fun sendPlayTechnologyCardIntentResponse(
        gameId: UUID,
        result: ActionResponse
    ) {
        sendToGame(gameId, "playTechnologyCardIntentResult", result)
    }

    fun sendPlayTechnologyCardResponse(gameId: UUID, result: ActionResponse) {
        sendToGame(gameId, "playTechnologyCardResult", result)
    }

    fun sendChangeCardResponse(gameId: UUID, result: ActionResponse) {
        sendToGame(gameId, "changeCardResult", result)
    }
}
