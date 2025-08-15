package de.niklaskerkhoff.wattsnextbackend.api.actions

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ActionWebSocketController {

    @MessageMapping("/action.roll-dice")
    @SendTo("/topic/public")
    fun rollDiceAction(): String {
        return "Action"
    }

    @MessageMapping("/action.play-card-intent")
    @SendTo("/topic/public")
    fun playCardIntent(request: PlayCardActionIntentRequest): String {
        return "Action"
    }

    @MessageMapping("/action.play-card")
    @SendTo("/topic/public")
    fun playCard(request: PlayCardActionRequest): String {
        return "Action"
    }
}
