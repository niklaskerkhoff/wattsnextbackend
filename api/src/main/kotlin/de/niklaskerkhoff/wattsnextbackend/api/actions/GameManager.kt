package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.api.actions.data.ActionResponse
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayClimateCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayTechnologyCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayTechnologyCardActionIntent
import de.niklaskerkhoff.wattsnextbackend.model.actions.RollDiceAction
import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import java.util.*

class GameManager(
    game: Game,
    val entityResolver: EntityResolver,
) {
    var game: Game = game
        private set

    private var previousAction: Action<*>? = null
    private var previousActionResult: Result<*>? = null

    fun getState() = ActionResponse<Unit>(game, ActionResponse.Status.Ok)

    fun handleRollDice(): ActionResponse<RollDiceAction.Information> {
        val action = RollDiceAction()
        return executeAction(action)
    }

    fun handlePlayClimateCard(climateCardId: UUID): ActionResponse<PlayClimateCardAction.Information> {
        val climateCard = entityResolver.getClimateCard(climateCardId)
            ?: return ActionResponse(game, ActionResponse.Status.IllegalAction)

        val action = PlayClimateCardAction(climateCard)
        return executeAction(action)
    }

    fun handlePlayTechnologyCardIntent(
        technologyCardId: UUID,
        targetPosition: Int
    ): ActionResponse<PlayTechnologyCardActionIntent.Information> {
        val technologyCard = entityResolver.getTechnologyCard(technologyCardId)
            ?: return ActionResponse(game, ActionResponse.Status.IllegalAction)

        val action = PlayTechnologyCardActionIntent(technologyCard, targetPosition)
        return executeAction(action)
    }

    fun handlePlayTechnologyCard(shallRecycle: Boolean): ActionResponse<PlayTechnologyCardAction.Information> {
        val previousAction = previousAction as? PlayTechnologyCardActionIntent
        val previousActionInformation =
            previousActionResult?.actionInformation as? PlayTechnologyCardActionIntent.Information

        if (previousAction == null || previousActionInformation == null)
            return ActionResponse(game, ActionResponse.Status.IllegalAction)

        val action = PlayTechnologyCardAction(
            shallRecycle,
            previousAction,
            previousActionInformation
        )
        return executeAction(action)
    }

    private fun <T> executeAction(action: Action<T>): ActionResponse<T> {
        if (!action.canExecute(game)) return ActionResponse(game, ActionResponse.Status.IllegalAction)

        val result = action.execute(game)

        previousAction = action
        previousActionResult = result

        return ActionResponse(result, ActionResponse.Status.IllegalAction)
    }
}
