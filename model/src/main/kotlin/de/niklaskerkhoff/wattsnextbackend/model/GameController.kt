package de.niklaskerkhoff.wattsnextbackend.model

import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardAction
import de.niklaskerkhoff.wattsnextbackend.model.actions.PlayCardActionIntent
import de.niklaskerkhoff.wattsnextbackend.model.actions.RollDiceAction
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game

class GameController(private var game: Game) {
    var previousAction: Action<*>? = null
    var previousActionResult: Any? = null

    fun executeAction(actionId: String, actionValues: Map<String, Any>) {

        val action = try {
            when (actionId) {
                "ROLL_DICE" -> RollDiceAction(game)

                "PLAY_CARD_INTENT" -> PlayCardActionIntent(
                    actionValues["progressCard"] as ProgressCard,
                    actionValues["targetPosition"] as Int,
                    game
                )

                "PLAY_CARD" -> PlayCardAction(
                    actionValues["shallRecycle"] as Boolean,
                    previousAction as PlayCardActionIntent,
                    previousActionResult as PlayCardActionIntent.Result,
                    game,
                    game.currentPlayer
                )

                else -> throw IllegalArgumentException("Unknown action ID.")
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid action values.")
        }
    }
}