package de.niklaskerkhoff.wattsnextbackend.api.actions.websockets

import de.niklaskerkhoff.wattsnextbackend.api.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.PlayClimateCardRequest
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.PlayTechnologyCardIntentRequest
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.PlayTechnologyCardRequest
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import java.util.*

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
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        val response = gameManager.handleRollDice()
        gameMessageSender.sendRollDiceResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playClimateCard")
    fun playClimateCard(request: PlayClimateCardRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        val response = gameManager.handlePlayClimateCard(request.climateCardId)
        gameMessageSender.sendPlayClimateCardResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playTechnologyCardIntent")
    fun playTechnologyCardIntent(request: PlayTechnologyCardIntentRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        val response = gameManager.handlePlayTechnologyCardIntent(request.progressCardId, request.targetPosition)
        gameMessageSender.sendPlayTechnologyCardIntentResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playTechnologyCard")
    fun playTechnologyCard(request: PlayTechnologyCardRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        val response = gameManager.handlePlayTechnologyCard(request.shallRecycle)
        gameMessageSender.sendPlayTechnologyCardResponse(sessionInfo.gameId, response)
    }

    private fun GameManagerRepo.getGameManagerOrThrow(gameId: UUID) =
        this.getGameManager(gameId) ?: throw IllegalArgumentException("Game not found")
}
