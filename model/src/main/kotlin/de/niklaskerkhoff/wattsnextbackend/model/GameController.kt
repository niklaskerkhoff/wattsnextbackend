package de.niklaskerkhoff.wattsnextbackend.model

import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardActionIntent
import de.niklaskerkhoff.wattsnextbackend.model.actions.RollDiceAction
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game

class GameController(private var game: Game) {
    private var previousAction: Action<*>? = null
    private var previousActionInformation: Any? = null

    fun executeAction(actionId: String, actionValues: Map<String, Any>): Any? {

        val action = try {
            when (actionId) {
                "ROLL_DICE" -> RollDiceAction()

                "PLAY_CARD_INTENT" -> PlayCardActionIntent(
                    actionValues["progressCard"] as ProgressCard,
                    actionValues["targetPosition"] as Int,
                )

                "PLAY_CARD" -> PlayCardAction(
                    actionValues["shallRecycle"] as Boolean,
                    previousAction as PlayCardActionIntent,
                    previousActionInformation as PlayCardActionIntent.Information,
                )

                else -> throw IllegalArgumentException("Unknown action ID.")
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid action values.")
        }

        previousAction = action
        previousActionInformation = action.execute(game)
        return previousActionInformation
    }
}