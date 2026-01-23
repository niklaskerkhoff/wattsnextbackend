package de.niklaskerkhoff.wattsnextbackend.app.actions.websockets

import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManager
import de.niklaskerkhoff.wattsnextbackend.app.actions.GameManagerRepo
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.ChangeCardRequest
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.PlayClimateCardRequest
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.PlayTechnologyCardIntentRequest
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.PlayTechnologyCardRequest
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Controller
@MessageMapping("/game")
class ActionWebSocketController(
    private val wsAuthHelper: WebSocketAuthHelper,
    private val gameMessageSender: GameMessageSender,
    private val gameManagerRepo: GameManagerRepo,
) {

    @MessageMapping("/earnMoney")
    fun rollDice(headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        authorize(sessionInfo.playerId, gameManager)
        val response = gameManager.handleRollDice()
        gameMessageSender.sendRollDiceResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playClimateCard")
    fun playClimateCard(request: PlayClimateCardRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        authorize(sessionInfo.playerId, gameManager)
        val response = gameManager.handlePlayClimateCard(request.climateCardId)
        gameMessageSender.sendPlayClimateCardResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playTechnologyCardIntent")
    fun playTechnologyCardIntent(request: PlayTechnologyCardIntentRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        authorize(sessionInfo.playerId, gameManager)
        val response = gameManager.handlePlayTechnologyCardIntent(request.progressCardId, request.targetPosition)
        gameMessageSender.sendPlayTechnologyCardIntentResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/playTechnologyCard")
    fun playTechnologyCard(request: PlayTechnologyCardRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        authorize(sessionInfo.playerId, gameManager)
        val response = gameManager.handlePlayTechnologyCard(request.shallRecycle)
        gameMessageSender.sendPlayTechnologyCardResponse(sessionInfo.gameId, response)
    }

    @MessageMapping("/changeCard")
    fun changeCard(request: ChangeCardRequest, headerAccessor: StompHeaderAccessor) {
        val sessionInfo = wsAuthHelper.getAndValidateSessionInfo(headerAccessor)
        val gameManager = gameManagerRepo.getGameManagerOrThrow(sessionInfo.gameId)
        authorize(sessionInfo.playerId, gameManager)
        val response = gameManager.handleChangeCard(request.progressCardId)
        gameMessageSender.sendChangeCardResponse(sessionInfo.gameId, response)
    }

    private fun authorize(playerId: UUID, gameManager: GameManager) {
        if (gameManager.game.currentPlayer.publicId != playerId) throw ResponseStatusException(HttpStatus.FORBIDDEN)
    }

    private fun GameManagerRepo.getGameManagerOrThrow(gameId: UUID) =
        this.getGameManager(gameId) ?: throw IllegalArgumentException("GAME_NOT_FOUND: $gameId")
}
