package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game
import kotlin.random.Random

class RollDiceAction(
    internal val game: Game,
) : Action<RollDiceAction.Result>() {
    override fun canExecute(): Boolean {
        return true
    }

    override fun execute(): Result {
        val value = Random.nextInt(1, 6)
        game.commonAssets.money += value
        return Result(value)
    }

    data class Result(val diceValue: Int)
}
