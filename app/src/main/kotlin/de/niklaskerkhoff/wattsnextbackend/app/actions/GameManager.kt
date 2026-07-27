package de.niklaskerkhoff.wattsnextbackend.app.actions

import de.niklaskerkhoff.wattsnextbackend.app.actions.data.ActionResponse
import de.niklaskerkhoff.wattsnextbackend.model.actions.*
import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import java.util.*

class GameManager(
    game: Game,
    val entityResolver: EntityResolver,
    lastActionTime: Long,
) {
    var game: Game = game
        private set

    var lastActionTime: Long = lastActionTime
        private set

    private var previousAction: Action<*>? = null
    private var previousActionResult: Result<*>? = null

    fun getState() = ActionResponse(game, ActionResponse.Status.Ok)

    fun handleRollDice(): ActionResponse {
        val action = RollDiceAction()
        return executeAction(action)
    }

    fun handlePlayClimateCard(climateCardId: UUID): ActionResponse {
        val climateCard = entityResolver.getClimateCard(climateCardId) ?: return illegalActionResponse()

        val action = PlayClimateCardAction(climateCard)
        return executeAction(action)
    }

    fun handlePlayTechnologyCardIntent(
        technologyCardId: UUID,
        targetPosition: Int,
    ): ActionResponse {
        val technologyCard = entityResolver.getTechnologyCard(technologyCardId) ?: return illegalActionResponse()

        val action = PlayTechnologyCardActionIntent(technologyCard, targetPosition)
        return executeAction(action)
    }

    fun handlePlayTechnologyCard(shallRecycle: Boolean): ActionResponse {
        val previousAction = previousAction as? PlayTechnologyCardActionIntent
        val previousActionInfo =
            previousActionResult?.actionInfo as? PlayTechnologyCardActionIntent.Info

        if (previousAction == null || previousActionInfo == null) return illegalActionResponse()

        // Recycling is an advanced-mode mechanic and currently disabled; ignore the client's flag.
        val action = PlayTechnologyCardAction(
            false,
            previousAction,
            previousActionInfo
        )
        return executeAction(action)
    }

    fun handleChangeCard(progressCardId: UUID): ActionResponse {
        if (previousAction is PlayTechnologyCardActionIntent) {
            return illegalActionResponse()
        }

        val progressCard = entityResolver.getProgressCard(progressCardId) ?: return illegalActionResponse()
        val action = ChangeCardAction(progressCard)
        return executeAction(action)
    }

    fun handleCancelGame(playerName: String): ActionResponse {
        val action = CancelGameAction(playerName)
        return executeAction(action)
    }

    fun handleAnswerQuiz(optionIndex: Int): ActionResponse {
        val action = AnswerQuizAction(optionIndex)
        return executeAction(action)
    }

    private fun <T> executeAction(action: Action<T>): ActionResponse {
        // While a quiz is pending, the only legal action is answering it.
        if (game.pendingQuiz != null && action !is AnswerQuizAction) return illegalActionResponse()

        if (!action.canExecute(game)) return illegalActionResponse()

        val result = action.execute(game)

        previousAction = action
        previousActionResult = result
        this.game = result.game

        lastActionTime = System.currentTimeMillis()
        return ActionResponse(result, ActionResponse.Status.Ok)
    }

    private fun illegalActionResponse() = ActionResponse(game, ActionResponse.Status.IllegalAction)
}
