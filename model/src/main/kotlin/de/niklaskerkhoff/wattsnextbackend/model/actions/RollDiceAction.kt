package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import kotlin.random.Random

class RollDiceAction(
) : Action<RollDiceAction.Info>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): Result<Info> {
        val value = Random.nextInt(1, 7)
        val updatedGame = game.withUpdatedMoney(value)

        return updatedGame.withNextTurn().let {
            Result(
                game = it.game,
                baseInfo = it.baseInfo,
                actionInfo = Info(value),
                cardEffectInfos = it.cardEffectInfos,
            )
        }
    }

    data class Info(val diceValue: Int)
}
