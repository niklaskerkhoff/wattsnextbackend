package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayClimateCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayTechnologyCardActionIntent
import de.niklaskerkhoff.wattsnextbackend.model.actions.RollDiceAction
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game

class GameManager(
    private var game: Game
) {
    private var previousAction: Action<*>? = null
    private var previousActionInformation: Any? = null

    fun handleRollDiceAction() {
        val action = RollDiceAction()
        executeAction(action)
    }

    fun handlePlayCardIntentAction(progressCard: ProgressCard, targetPosition: Int): Any {
        val action = PlayTechnologyCardActionIntent(progressCard, targetPosition)
         executeAction(action)
    }

    fun handlePlayCardAction(shallRecycle: Boolean) {
        val action = PlayClimateCardAction(
            shallRecycle,
            previousAction as PlayTechnologyCardActionIntent,
            previousActionInformation as PlayTechnologyCardActionIntent.Information
        )
        executeAction(action)
    }

    fun getStateInfo(): Game = game

    private fun <T> executeAction(action: Action<T>): Boolean {
        if (!action.canExecute(game)) return false
        previousAction = action
        previousActionInformation = action.execute(game)

        return true
    }
}
