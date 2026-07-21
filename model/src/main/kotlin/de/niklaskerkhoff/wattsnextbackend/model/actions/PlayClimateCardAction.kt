package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst

class PlayClimateCardAction(
    internal val climateCard: ProgressCard.ClimateCard,
) : Action<PlayClimateCardAction.ActionInfo>() {

    override fun canExecute(game: Game): Boolean {
        return game.money >= game.getModifiedMoneyCost(climateCard, -1) &&
                game.resources >= game.getModifiedResourceCost(climateCard, -1) &&
                game.climateCards.size < 10
    }

    override fun execute(game: Game): Result<ActionInfo> {
        val updatedClimateCards = game.climateCards + climateCard

        val moneyAfterCardPlayed = game.money - game.getModifiedMoneyCost(climateCard, -1)
        val resourcesAfterCardPlayed =
            game.resources - game.getModifiedResourceCost(climateCard, -1)

        val currentPlayerProgressCardsWithoutPlayedCard = game.currentPlayer.progressCards.removed(climateCard)

        // Draw a ProgressCard
        val (drawnCard, updatedProgressDeck, updatedProgressDiscardPile) = game.drawProgressCard()

        val currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard =
            currentPlayerProgressCardsWithoutPlayedCard + drawnCard

        // Update
        val updatedCurrentPlayer =
            game.currentPlayer.copy(progressCards = currentPlayerProgressCardsWithoutPlayedCardWithDrawnCard)
        val updatedPlayers = game.players.replacedFirst(game.currentPlayer, updatedCurrentPlayer)

        val gameAfterCardPlayed = game.copy(
            players = updatedPlayers,
            money = moneyAfterCardPlayed,
            resources = resourcesAfterCardPlayed,
            climateCards = updatedClimateCards,
            progressCardDeck = updatedProgressDeck,
            progressCardDiscardPile = updatedProgressDiscardPile,
        )

        val (gameAfterEffect, cardEffectInfos) = climateCard.effect(gameAfterCardPlayed)

        return gameAfterEffect.withNextTurn().let {
            Result(
                game = it.game,
                baseInfo = it.baseInfo,
                actionInfo = ActionInfo(climateCard, drawnCard),
                cardEffectInfos = cardEffectInfos,
                eventEffectInfos = it.eventEffectInfos,
            )
        }
    }

    data class ActionInfo(
        val playedCard: ProgressCard.ClimateCard,
        val drawnCard: ProgressCard,
    )
}
