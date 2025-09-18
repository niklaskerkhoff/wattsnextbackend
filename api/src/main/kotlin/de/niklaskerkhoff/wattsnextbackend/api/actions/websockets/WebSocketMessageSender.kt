package de.niklaskerkhoff.wattsnextbackend.api.actions.websockets

import de.niklaskerkhoff.wattsnextbackend.api.actions.data.ActionResponse
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.api.gameinit.GameInit
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayClimateCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayTechnologyCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayTechnologyCardActionIntent
import de.niklaskerkhoff.wattsnextbackend.model.actions.RollDiceAction
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

    fun sendRollDiceResponse(gameId: UUID, result: ActionResponse<RollDiceAction.Information>) {
        sendToGame(gameId, "earnMoneyResult", result)
    }

    fun sendPlayClimateCardResponse(gameId: UUID, result: ActionResponse<PlayClimateCardAction.Information>) {
        sendToGame(gameId, "playClimateCardResult", result)
    }

    fun sendPlayTechnologyCardIntentResponse(
        gameId: UUID,
        result: ActionResponse<PlayTechnologyCardActionIntent.Information>
    ) {
        sendToGame(gameId, "playTechnologyCardIntentResult", result)
    }

    fun sendPlayTechnologyCardResponse(gameId: UUID, result: ActionResponse<PlayTechnologyCardAction.Information>) {
        sendToGame(gameId, "playTechnologyCardResult", result)
    }
}
