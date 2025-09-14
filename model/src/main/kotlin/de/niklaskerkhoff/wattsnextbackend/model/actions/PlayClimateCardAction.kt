package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInformation
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst

class PlayClimateCardAction(
    internal val climateCard: ProgressCard.ClimateCard,
) : Action<PlayClimateCardAction.Information>() {

    override fun canExecute(game: Game): Boolean {
        return game.money >= climateCard.moneyCosts.modified(climateCard, game, Pair(game, -1)) &&
                game.resources >= climateCard.resourceCosts.modified(climateCard, game, Pair(game, -1)) &&
                game.climateCards.size < 10
    }

    override fun execute(game: Game): Result<Information> {
        val updatedClimateCards = game.climateCards + climateCard

        val moneyAfterCardPlayed = game.money - climateCard.moneyCosts.modified(climateCard, game, Pair(game, -1))
        val resourcesAfterCardPlayed = game.money - climateCard.resourceCosts.modified(climateCard, game, Pair(game, -1))

        val currentPlayerProgressCardsWithoutPlayedCard = game.currentPlayer.progressCards.removed(climateCard)

        // Draw a ProgressCard
        val (drawnCard, updatedProgressDeck) = game.progressCardDeck.removedLast()

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
        )

        val (gameAfterEffect, cardEffectInformations) =
            climateCard.effect?.let { it(gameAfterCardPlayed) } ?: Pair(gameAfterCardPlayed, emptyList())

        return gameAfterEffect.withNextTurn().let {
            Result(
                game = it.game,
                baseInformation = it.baseInformation,
                actionInformation = Information(climateCard, drawnCard, cardEffectInformations)
            )
        }
    }

    data class Information(
        val playedCard: ProgressCard.ClimateCard,
        val drawnCard: ProgressCard,
        val cardEffectInformations: List<CardEffectInformation>
    )
}
