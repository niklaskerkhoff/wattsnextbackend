package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.ActionResult
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst
import kotlin.math.floor

class PlayTechnologyCardAction(
    internal val shallRecycle: Boolean,
    internal val playTechnologyCardActionIntent: PlayTechnologyCardActionIntent,
    internal val playTechnologyCardActionIntentInformation: PlayTechnologyCardActionIntent.Information,
) : Action<PlayTechnologyCardAction.Information>() {

    override fun canExecute(game: Game): Boolean {
        // Already checked in PlayTechnologyCardActionIntent
        return true
    }

    override fun execute(game: Game): ActionResult<Information> {
        val (gameAfterRecycle, recyclingInformation) =
            if (shallRecycle) {
                if (!playTechnologyCardActionIntentInformation.canRecycle) {
                    throw IllegalArgumentException("Cannot recycle.")
                }
                recycle(game)
            } else {
                ActionResult(game, null)
            }

        val resultAfterCardPlayed = playCard(gameAfterRecycle)

        return ActionResult(
            resultAfterCardPlayed.game,
            Information(
                playedCard = playTechnologyCardActionIntent.technologyCard,
                targetPosition = playTechnologyCardActionIntent.targetPosition,
                drawnCard = resultAfterCardPlayed.information.drawnCard,
                payedMoneyForCard = resultAfterCardPlayed.information.payedMoneyForCard,
                payedResourcesForCard = resultAfterCardPlayed.information.payedResourcesForCard,
                didRecycle = shallRecycle,
                payedMoneyForRecycling = recyclingInformation?.payedMoneyForRecycling,
                gainedResourcesForRecycling = recyclingInformation?.gainedResourcesForRecycling,
            )
        )
    }

    private fun recycle(game: Game): ActionResult<RecyclingInformation> {
        val currentCard = game.technologyBoard.getCurrentTechnologyCard(
            playTechnologyCardActionIntent.technologyCard.technology,
            playTechnologyCardActionIntent.targetPosition
        ) ?: throw IllegalStateException("Previous card not found.")

        val moneyForRecycling = currentCard.values.resourceCosts
        val gainingResources = floor(currentCard.values.resourceCosts / 2.0).toInt()

        val updatedMoney = game.money - currentCard.values.resourceCosts
        val updatedResources = game.resources + gainingResources

        return ActionResult(
            game.copy(money = updatedMoney, resources = updatedResources),

            RecyclingInformation(
                moneyForRecycling, gainingResources
            )
        )
    }

    private fun playCard(game: Game): ActionResult<PlayCardInformation> {

        // Play the ProgressCard
        val technologyBoardWithPlayedCard = game.technologyBoard.withCardPlayed(
            playTechnologyCardActionIntent.technologyCard,
            playTechnologyCardActionIntent.targetPosition
        )

        val moneyToPay = playTechnologyCardActionIntent.technologyCard.values.moneyCosts
        val resourcesToPay = playTechnologyCardActionIntent.technologyCard.values.resourceCosts

        val moneyAfterCardPlayed = game.money - moneyToPay
        val resourcesAfterCardPlayed = game.resources - resourcesToPay

        val currentPlayerProgressCardsWithoutPlayedCard = game.currentPlayer.progressCards.removed(
            playTechnologyCardActionIntent.technologyCard,
        )

        // Draw a ProgressCard
        val (drawnCard, updatedProgressDeck) = game.progressCardDeck.removedLast()

        val currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard =
            currentPlayerProgressCardsWithoutPlayedCard + drawnCard

        // Update
        val updatedCurrentPlayer =
            game.currentPlayer.copy(progressCards = currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard)
        val updatedPlayers = game.players.replacedFirst(game.currentPlayer, updatedCurrentPlayer)

        return ActionResult(
            game.copy(
                players = updatedPlayers,
                money = moneyAfterCardPlayed,
                resources = resourcesAfterCardPlayed,
                technologyBoard = technologyBoardWithPlayedCard,
                progressCardDeck = updatedProgressDeck,
            ).withNextTurn(),
            PlayCardInformation(
                drawnCard = drawnCard,
                payedMoneyForCard = moneyToPay,
                payedResourcesForCard = resourcesToPay,
            )
        )
    }

    data class RecyclingInformation(
        val payedMoneyForRecycling: Int,
        val gainedResourcesForRecycling: Int,
    )

    data class PlayCardInformation(
        val drawnCard: ProgressCard,
        val payedMoneyForCard: Int,
        val payedResourcesForCard: Int,
    )

    data class Information(
        val playedCard: ProgressCard.TechnologyCard,
        val targetPosition: Int,
        val drawnCard: ProgressCard,
        val payedMoneyForCard: Int,
        val payedResourcesForCard: Int,
        val didRecycle: Boolean,
        val payedMoneyForRecycling: Int?,
        val gainedResourcesForRecycling: Int?,
    )
}
