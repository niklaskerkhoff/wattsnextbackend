package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.Action
import de.niklaskerkhoff.wattsnextbackend.model.Game
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.types.Technology
import kotlin.math.floor

class PlayCardAction(
    internal val shallRecycle: Boolean,
    internal val playCardRequestAction: PlayCardRequestAction,
    internal val game: Game,
) : Action<PlayCardAction.Result>() {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute(): Result {
        if (shallRecycle) {
            recycle()
        }
        game.commonAssets.technologyBoard.playProgressCard(
            playCardRequestAction.progressCard,
            playCardRequestAction.targetPosition
        )
        game.commonAssets.money -= playCardRequestAction.progressCard.values.moneyCosts

        return Result(
            playedCard = playCardRequestAction.progressCard,
            targetTechnology = playCardRequestAction.progressCard.technology,
            targetPosition = playCardRequestAction.targetPosition,
//            drawnCard =
        )
    }

    private fun recycle() {
        val previousCard = game.commonAssets.technologyBoard.getCard(
            playCardRequestAction.progressCard.technology,
            playCardRequestAction.targetPosition
        ) ?: throw IllegalArgumentException("Previous card not found.")

        if (previousCard.values.moneyCosts + playCardRequestAction.progressCard.values.moneyCosts > game.commonAssets.money) {
            throw IllegalArgumentException("Not enough money to recycle.")
        }

        game.commonAssets.money -= previousCard.values.moneyCosts
        game.commonAssets.resources += floor(previousCard.values.resourceCosts / 2.0).toInt()
    }


    data class Result(
        val playedCard: ProgressCard,
        val targetTechnology: Technology,
        val targetPosition: Int,
//        val drawnCard: ProgressCard
    )
}
