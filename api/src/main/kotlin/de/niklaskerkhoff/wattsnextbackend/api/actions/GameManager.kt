package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardActionIntent
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

    fun handlePlayCardIntentAction(progressCard: ProgressCard, targetPosition: Int) {
        val action = PlayCardActionIntent(progressCard, targetPosition)
        executeAction(action)
    }

    fun handlePlayCardAction(shallRecycle: Boolean) {
        val action = PlayCardAction(
            shallRecycle,
            previousAction as PlayCardActionIntent,
            previousActionInformation as PlayCardActionIntent.Information
        )
        executeAction(action)
    }

    private fun <T> executeAction(action: Action<T>) {
        if (!action.canExecute(game)) throw IllegalStateException("Action cannot be executed.")
        previousAction = action
        previousActionInformation = action.execute(game)
    }
}
