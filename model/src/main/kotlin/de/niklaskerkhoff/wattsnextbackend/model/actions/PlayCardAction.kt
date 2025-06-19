package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.Action
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.state.Game
import de.niklaskerkhoff.wattsnextbackend.model.state.Player
import de.niklaskerkhoff.wattsnextbackend.model.types.ProgressCardType
import kotlin.math.floor

class PlayCardAction(
    internal val shallRecycle: Boolean,
    internal val playCardActionIntent: PlayCardActionIntent,
    internal val playCardActionIntentResult: PlayCardActionIntent.Result,
    internal val game: Game,
    internal val currentPlayer: Player,
) : Action<PlayCardAction.Result>() {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute(): Result {
        if (shallRecycle) {
            if (!playCardActionIntentResult.canRecycle) {
                throw IllegalArgumentException("Cannot recycle.")
            }
            recycle()
        }

        val drawnCard = playCard()

        return Result(
            playedCard = playCardActionIntent.progressCard,
            targetProgressCardType = playCardActionIntent.progressCard.progressCardType,
            targetPosition = playCardActionIntent.targetPosition,
            drawnCard = drawnCard
        )
    }

    private fun recycle() {
        val currentCard = game.commonAssets.getCurrentProgressCard(
            playCardActionIntent.progressCard.progressCardType,
            playCardActionIntent.targetPosition
        ) ?: throw IllegalStateException("Previous card not found.")

        game.commonAssets.money -= currentCard.values.moneyCosts
        game.commonAssets.resources += floor(currentCard.values.resourceCosts / 2.0).toInt()
    }

    private fun playCard(): ProgressCard? {
        game.commonAssets.playProgressCard(
            playCardActionIntent.progressCard,
            playCardActionIntent.targetPosition
        )
        game.commonAssets.money -= playCardActionIntent.progressCard.values.moneyCosts

        val drawnCard = game.commonAssets.drawProgressCardFromDeck()

        currentPlayer.replaceProgressCard(playCardActionIntent.progressCard, drawnCard)

        return drawnCard
    }


    data class Result(
        val playedCard: ProgressCard,
        val targetProgressCardType: ProgressCardType,
        val targetPosition: Int,
        val drawnCard: ProgressCard?
    )
}
