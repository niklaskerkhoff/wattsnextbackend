package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.state.Action
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModificationApplier
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.WithTargetPositionModifierProvider
import de.niklaskerkhoff.wattsnextbackend.model.state.Game

class PlayCardActionIntent(
    internal val progressCard: ProgressCard,
    internal val targetPosition: Int,
) : Action<PlayCardActionIntent.Information>() {
    override fun canExecute(game: Game): Boolean {
        return true
    }

    override fun execute(game: Game): ActionResult<Information> {
        /*val currentProgressCard =
            game.commonAssets.technologyBoard.getCurrentProgressCard(progressCard.technology, targetPosition)
*/
        val (canRecycle, moneyToRecycle) =Pair(false, null)
            /*if (currentProgressCard == null) {
                Pair(false, null)
            } else {
                val moneyToRecycle = currentProgressCard.values.moneyCosts
                val presentMoney = game.commonAssets.money - progressCard.values.moneyCosts
                val hasEnoughMoney = presentMoney >= moneyToRecycle
                Pair(hasEnoughMoney, moneyToRecycle)
            }*/

        val moneyToPay = ModificationApplier(
            WithTargetPositionModifierProvider({ costModifier }, progressCard, game, targetPosition),
            progressCard.values.moneyCosts,
            game,
        ).applyModification()

        return ActionResult(
            game,
            Information(
                canRecycle = canRecycle,
                moneyToRecycle = moneyToRecycle,
                moneyToPay = moneyToPay,
                resourcesToPay = progressCard.values.resourceCosts,
            )
        )
    }

    data class Information(
        val canRecycle: Boolean,
        val moneyToRecycle: Int?,
        val moneyToPay: Int,
        val resourcesToPay: Int,
    )
}