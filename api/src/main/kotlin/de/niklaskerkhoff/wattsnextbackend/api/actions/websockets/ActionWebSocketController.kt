package de.niklaskerkhoff.wattsnextbackend.api.actions.websockets

import de.niklaskerkhoff.wattsnextbackend.api.actions.data.PlayCardActionIntentRequest
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.PlayCardActionRequest
import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManagerRepo
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("/game")
class ActionWebSocketController(
    private val wsAuthHelper: WebSocketAuthHelper,
    private val gameMessageSender: GameMessageSender,
    private val gameManagerRepo: GameManagerRepo,
) {

    @MessageMapping("/earnMoney")
    fun rollDiceAction(headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        sessionInfo.gameId.let { gameId ->
            val game = gameManagerRepo.getGameManager(gameId)
        }
    }

    @MessageMapping("/playTechnologyCardIntent")
    fun playTechnologyCardIntent(request: PlayCardActionIntentRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }

    @MessageMapping("/playTechnologyCard")
    fun playTechnologyCard(request: PlayCardActionRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }

    @MessageMapping("/playClimateCard")
    fun playClimateCard(request: PlayCardActionRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }
}
