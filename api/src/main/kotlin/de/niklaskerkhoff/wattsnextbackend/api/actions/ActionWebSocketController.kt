package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("/game")
class ActionWebSocketController(
    private val wsAuthHelper: WebSocketAuthHelper
) {

    @MessageMapping("/earnMoney")
    fun rollDiceAction(headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }

    @MessageMapping("/playCardIntent")
    fun playCardIntent(request: PlayCardActionIntentRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }

    @MessageMapping("/playCard")
    fun playCard(request: PlayCardActionRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        // TODO: process action
    }
}
