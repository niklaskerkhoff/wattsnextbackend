package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import de.niklaskerkhoff.wattsnextbackend.model.core.Result

class CancelGameAction(internal val playerName: String) : Action<CancelGameAction.Info>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): Result<Info> {
        val updatedGame = game.withUpdatedState(GameState.Cancelled)

        return Result(
            game = updatedGame,
            baseInfo = Game.BaseInfo(hasGameStateChanged = true),
            actionInfo = Info(playerName = playerName)
        )
    }

    data class Info(val playerName: String)
}
