package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst
import kotlin.math.floor

class PlayTechnologyCardAction(
    internal val shallRecycle: Boolean,
    internal val intent: PlayTechnologyCardActionIntent,
    internal val intentInfo: PlayTechnologyCardActionIntent.Info,
) : Action<PlayTechnologyCardAction.ActionInfo>() {

    override fun canExecute(game: Game): Boolean {
        // Already checked in PlayTechnologyCardActionIntent
        return true
    }

    override fun execute(game: Game): Result<ActionInfo> {
        val (gameAfterRecycle, _, recyclingInfo) =
            if (shallRecycle) {
                if (!intentInfo.canRecycle) {
                    throw IllegalArgumentException("Cannot recycle.")
                }
                recycle(game)
            } else {
                Result(game)
            }

        val resultAfterCardPlayed = playCard(gameAfterRecycle)

        return Result(
            game = resultAfterCardPlayed.game,
            baseInfo = resultAfterCardPlayed.baseInfo,
            actionInfo = ActionInfo(
                playedCard = intent.technologyCard,
                targetPosition = intent.targetPosition,
                drawnCard = resultAfterCardPlayed.actionInfo!!.drawnCard,
                payedMoneyForCard = resultAfterCardPlayed.actionInfo.payedMoneyForCard,
                payedResourcesForCard = resultAfterCardPlayed.actionInfo.payedResourcesForCard,
                didRecycle = shallRecycle,
                payedMoneyForRecycling = recyclingInfo?.payedMoneyForRecycling,
                gainedResourcesForRecycling = recyclingInfo?.gainedResourcesForRecycling,
            ),
            cardEffectInfos = resultAfterCardPlayed.cardEffectInfos,
            eventEffectInfos = resultAfterCardPlayed.eventEffectInfos,
        )
    }

    private fun recycle(game: Game): Result<RecyclingInfo> {
        val currentCard = game.technologyBoard.getCurrentTechnologyCard(
            intent.technologyCard.supply.base.technology,
            intent.targetPosition
        ) ?: throw IllegalStateException("Previous card not found.")

        val moneyForRecycling = currentCard.resourceCosts.base
        val gainingResources = floor(currentCard.resourceCosts.base / 2.0).toInt()

        val updatedMoney = game.money - moneyForRecycling
        val updatedResources = game.resources + gainingResources

        return Result(
            game = game.copy(money = updatedMoney, resources = updatedResources),
            actionInfo = RecyclingInfo(
                payedMoneyForRecycling = moneyForRecycling,
                gainedResourcesForRecycling = gainingResources
            )
        )
    }

    private fun playCard(game: Game): Result<PlayCardInfo> {

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
        val (drawnCard, updatedProgressDeck, updatedProgressDiscardPile) = game.drawProgressCard()

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
            progressCardDiscardPile = updatedProgressDiscardPile,
        )

        val (gameAfterEffect, cardEffectInfos) = intent.technologyCard.effect(updatedGame)

        return gameAfterEffect.withNextTurn().let {
            Result(
                game = it.game,
                baseInfo = it.baseInfo,
                actionInfo = PlayCardInfo(
                    drawnCard = drawnCard,
                    payedMoneyForCard = moneyToPay,
                    payedResourcesForCard = resourcesToPay,
                ),
                cardEffectInfos = cardEffectInfos,
                eventEffectInfos = it.eventEffectInfos,
            )
        }
    }

    data class RecyclingInfo(
        val payedMoneyForRecycling: Int,
        val gainedResourcesForRecycling: Int,
    )

    data class PlayCardInfo(
        val drawnCard: ProgressCard,
        val payedMoneyForCard: Int,
        val payedResourcesForCard: Int,
    )

    data class ActionInfo(
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
