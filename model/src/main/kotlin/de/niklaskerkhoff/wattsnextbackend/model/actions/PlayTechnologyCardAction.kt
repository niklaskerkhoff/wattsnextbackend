package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst
import kotlin.math.floor

class PlayTechnologyCardAction(
    internal val shallRecycle: Boolean,
    internal val intent: PlayTechnologyCardActionIntent,
    internal val intentInformation: PlayTechnologyCardActionIntent.Information,
) : Action<PlayTechnologyCardAction.ActionInformation>() {

    override fun canExecute(game: Game): Boolean {
        // Already checked in PlayTechnologyCardActionIntent
        return true
    }

    override fun execute(game: Game): Result<ActionInformation> {
        val (gameAfterRecycle, _, recyclingInformation) =
            if (shallRecycle) {
                if (!intentInformation.canRecycle) {
                    throw IllegalArgumentException("Cannot recycle.")
                }
                recycle(game)
            } else {
                Result(game)
            }

        val resultAfterCardPlayed = playCard(gameAfterRecycle)

        return Result(
            game = resultAfterCardPlayed.game,
            actionInformation = ActionInformation(
                playedCard = intent.technologyCard,
                targetPosition = intent.targetPosition,
                drawnCard = resultAfterCardPlayed.actionInformation!!.drawnCard,
                payedMoneyForCard = resultAfterCardPlayed.actionInformation.payedMoneyForCard,
                payedResourcesForCard = resultAfterCardPlayed.actionInformation.payedResourcesForCard,
                didRecycle = shallRecycle,
                payedMoneyForRecycling = recyclingInformation?.payedMoneyForRecycling,
                gainedResourcesForRecycling = recyclingInformation?.gainedResourcesForRecycling,
            )
        )
    }

    private fun recycle(game: Game): Result<RecyclingInformation> {
        val currentCard = game.technologyBoard.getCurrentTechnologyCard(
            intent.technologyCard.supply.technology,
            intent.targetPosition
        ) ?: throw IllegalStateException("Previous card not found.")

        val moneyForRecycling = currentCard.resourceCosts.base
        val gainingResources = floor(currentCard.resourceCosts.base / 2.0).toInt()

        val updatedMoney = game.money - moneyForRecycling
        val updatedResources = game.resources + gainingResources

        return Result(
            game = game.copy(money = updatedMoney, resources = updatedResources),
            actionInformation = RecyclingInformation(
                payedMoneyForRecycling = moneyForRecycling,
                gainedResourcesForRecycling = gainingResources
            )
        )
    }

    private fun playCard(game: Game): Result<PlayCardInformation> {

        // Play the ProgressCard
        val technologyBoardWithPlayedCard = game.technologyBoard.withCardPlayed(
            intent.technologyCard,
            intent.targetPosition
        )

        val moneyToPay = intent.technologyCard.moneyCosts
            .modified(intent.technologyCard, game, Pair(game, intent.targetPosition))
        val resourcesToPay = intent.technologyCard.resourceCosts
            .modified(intent.technologyCard, game, Pair(game, intent.targetPosition))

        val moneyAfterCardPlayed = game.money - moneyToPay
        val resourcesAfterCardPlayed = game.resources - resourcesToPay

        val currentPlayerProgressCardsWithoutPlayedCard = game.currentPlayer.progressCards.removed(
            intent.technologyCard,
        )

        // Draw a ProgressCard
        val (drawnCard, updatedProgressDeck) = game.progressCardDeck.removedLast()

        val currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard =
            currentPlayerProgressCardsWithoutPlayedCard + drawnCard

        // Update
        val updatedCurrentPlayer =
            game.currentPlayer.copy(progressCards = currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard)
        val updatedPlayers = game.players.replacedFirst(game.currentPlayer, updatedCurrentPlayer)

        val updatedGame = game.copy(
            players = updatedPlayers,
            money = moneyAfterCardPlayed,
            resources = resourcesAfterCardPlayed,
            technologyBoard = technologyBoardWithPlayedCard,
            progressCardDeck = updatedProgressDeck,
        )

        val (gameAfterEffect, cardEffectInformations) = intent.technologyCard.effect(updatedGame)

        return gameAfterEffect.withNextTurn().let {
            Result(
                game = it.game,
                baseInformation = it.baseInformation,
                actionInformation = PlayCardInformation(
                    drawnCard = drawnCard,
                    payedMoneyForCard = moneyToPay,
                    payedResourcesForCard = resourcesToPay,
                ),
                cardEffectInformations = cardEffectInformations + it.cardEffectInformations,
            )
        }
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

    data class ActionInformation(
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
