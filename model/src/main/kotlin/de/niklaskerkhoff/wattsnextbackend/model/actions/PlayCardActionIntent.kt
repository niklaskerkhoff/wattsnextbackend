package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.Action
import de.niklaskerkhoff.wattsnextbackend.model.state.Game
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard

class PlayCardActionIntent(
    internal val progressCard: ProgressCard,
    internal val targetPosition: Int,
    internal val game: Game,
) : Action<PlayCardActionIntent.Result>() {
    override fun canExecute(): Boolean {
        return true
    }

    override fun execute(): Result {
        val currentProgressCard =
            game.commonAssets.getCurrentProgressCard(progressCard.progressCardType, targetPosition)

        val (canRecycle, moneyToRecycle) =
            if (currentProgressCard == null) {
                Pair(false, null)
            } else {
                val moneyToRecycle = currentProgressCard.values.moneyCosts
                val presentMoney = game.commonAssets.money - progressCard.values.moneyCosts
                val hasEnoughMoney = presentMoney >= moneyToRecycle
                Pair(hasEnoughMoney, moneyToRecycle)
            }

        return Result(
            canRecycle = canRecycle,
            moneyToRecycle = moneyToRecycle,
            moneyToPay = progressCard.values.moneyCosts,
            resourcesToPay = progressCard.values.resourceCosts,
        )
    }

    data class Result(
        val canRecycle: Boolean,
        val moneyToRecycle: Int?,
        val moneyToPay: Int,
        val resourcesToPay: Int,
    )
}