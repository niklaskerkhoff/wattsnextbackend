package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst
import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game
import kotlin.math.floor

class PlayCardAction(
    internal val shallRecycle: Boolean,
    internal val playCardActionIntent: PlayCardActionIntent,
    internal val playCardActionIntentInformation: PlayCardActionIntent.Information,
) : Action<PlayCardAction.Information>() {

    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): ActionResult<Information> {
        val gameAfterRecycle =
            if (shallRecycle) {
                if (!playCardActionIntentInformation.canRecycle) {
                    throw IllegalArgumentException("Cannot recycle.")
                }
                recycle(game)
            } else {
                game
            }

        val resultAfterCardPlayed = playCard(gameAfterRecycle)

        return ActionResult(
            resultAfterCardPlayed.game,
            Information(
                playedCard = playCardActionIntent.progressCard,
                targetPosition = playCardActionIntent.targetPosition,
                drawnCard = resultAfterCardPlayed.information
            )
        )
    }

    private fun recycle(game: Game): Game {
        val currentCard = game.commonAssets.technologyBoard.getCurrentTechonologyProgressCard(
            (playCardActionIntent.progressCard as ProgressCard.TechnologyCard).technology,
            playCardActionIntent.targetPosition
        ) ?: throw IllegalStateException("Previous card not found.")

        val updatedMoney = game.commonAssets.money - currentCard.values.moneyCosts
        val updatedResources = game.commonAssets.resources + floor(currentCard.values.resourceCosts / 2.0).toInt()

        return game.copy(commonAssets = game.commonAssets.copy(money = updatedMoney, resources = updatedResources))
    }

    private fun playCard(game: Game): ActionResult<ProgressCard?> {

        // Play the ProgressCard
        val technologyBoardWithPlayedCard = game.commonAssets.technologyBoard.withProgressCardPlayed(
            playCardActionIntent.progressCard,
            playCardActionIntent.targetPosition
        )

        val moneyAfterCardPlayed = game.commonAssets.money - playCardActionIntent.progressCard.values.moneyCosts
        val resourcesAfterCardPlayed =
            game.commonAssets.resources - playCardActionIntent.progressCard.values.resourceCosts

        val currentPlayerProgressCardsWithoutPlayedCard = game.currentPlayer.progressCards.removed(
            playCardActionIntent.progressCard,
        )

        // Draw a ProgressCard
        val drawnCard = game.commonAssets.progressCardDeck.last()

        val currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard =
            currentPlayerProgressCardsWithoutPlayedCard + drawnCard

        // Update

        val updatedCurrentPlayer =
            game.currentPlayer.copy(progressCards = currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard)
        val updatedPlayers = game.players.replacedFirst(game.currentPlayer, updatedCurrentPlayer)

        val updatedProgressDeck = game.commonAssets.progressCardDeck.removedLast()

        val updatedCommonAssets = game.commonAssets.copy(
            technologyBoard = technologyBoardWithPlayedCard,
            money = moneyAfterCardPlayed,
            resources = resourcesAfterCardPlayed,
            progressCardDeck = updatedProgressDeck,
        )

        return ActionResult(
            game.copy(
                players = updatedPlayers,
                commonAssets = updatedCommonAssets,
            ),
            drawnCard
        )
    }


    data class Information(
        val playedCard: ProgressCard,
        val targetPosition: Int,
        val drawnCard: ProgressCard?
    )
}
