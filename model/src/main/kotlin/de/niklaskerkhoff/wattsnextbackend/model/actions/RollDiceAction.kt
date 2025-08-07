package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game
import kotlin.random.Random

class RollDiceAction(
) : Action<RollDiceAction.Information>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): ActionResult<Information> {
        val value = Random.nextInt(1, 6)
        val updatedMoney = game.commonAssets.money + value

        val updatedGame = game.copy(commonAssets = game.commonAssets.copy(money = updatedMoney))
        return ActionResult(updatedGame, Information(value))
    }

    data class Information(val diceValue: Int)
}
