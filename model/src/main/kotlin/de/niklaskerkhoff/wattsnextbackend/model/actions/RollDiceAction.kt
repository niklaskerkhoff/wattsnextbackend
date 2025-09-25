package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import kotlin.random.Random

class RollDiceAction(
) : Action<RollDiceAction.Information>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): Result<Information> {
        val value = Random.nextInt(1, 7)
        val updatedGame = game.withUpdatedMoney(value)

        return updatedGame.withNextTurn().let {
            Result(
                game = it.game,
                baseInformation = it.baseInformation,
                actionInformation = Information(value),
                cardEffectInformations = it.cardEffectInformations,
            )
        }
    }

    data class Information(val diceValue: Int)
}
