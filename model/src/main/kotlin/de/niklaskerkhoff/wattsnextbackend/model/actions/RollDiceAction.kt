package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import kotlin.random.Random

class RollDiceAction(
) : Action<RollDiceAction.Information>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): ActionResult<Information> {
        val value = Random.nextInt(1, 6)
        val updatedMoney = game.money + value
        val updatedGame = game.copy(money = updatedMoney)

        return ActionResult(updatedGame, Information(value))
    }

    data class Information(val diceValue: Int)
}
