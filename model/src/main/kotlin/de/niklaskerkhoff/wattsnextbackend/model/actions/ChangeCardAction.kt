package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst

class ChangeCardAction(
    internal val progressCard: ProgressCard,
) : Action<ChangeCardAction.Info>() {
    override fun canExecute(game: Game): Boolean {
        return game.money >= 1 &&
                (game.progressCardDeck.isNotEmpty() || game.progressCardDiscardPile.isNotEmpty())
    }

    override fun execute(game: Game): Result<Info> {
        val (drawnCard, updatedProgressDeck, updatedProgressDiscardPile) = game.drawProgressCard()
        val updatedHandcards = game.currentPlayer.progressCards.removed(progressCard) + drawnCard
        val updatedCurrentPlayer =
            game.currentPlayer.copy(progressCards = updatedHandcards)
        val updatedPlayers = game.players.replacedFirst(game.currentPlayer, updatedCurrentPlayer)
        val updatedMoney = game.money - 1
        val updatedGame = game.copy(
            players = updatedPlayers,
            money = updatedMoney,
            progressCardDeck = updatedProgressDeck,
            // The changed-out card goes to the discard pile to be reshuffled when the draw pile is empty.
            progressCardDiscardPile = updatedProgressDiscardPile + progressCard,
        )

        return Result(
            game = updatedGame,
            actionInfo = Info(
                discardedCard = progressCard,
                drawnCard = drawnCard,
            )
        )
    }

    data class Info(
        val discardedCard: ProgressCard,
        val drawnCard: ProgressCard,
    )
}