package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModificationApplier
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.WithTargetPositionModifierProvider

class PlayCardActionIntent(
    internal val progressCard: ProgressCard,
    internal val targetPosition: Int,
) : Action<PlayCardActionIntent.Information>() {
    override fun canExecute(game: Game): Boolean {
        return game.money >= progressCard.values.moneyCosts && game.resources >= progressCard.values.resourceCosts
    }

    override fun execute(game: Game): ActionResult<Information> {
        return when (progressCard) {
            is ProgressCard.TechnologyCard -> handleTechnologyCard(game, progressCard)
            is ProgressCard.ClimateCard -> handleClimateCard(game, progressCard)
        }
    }

    private fun handleTechnologyCard(
        game: Game,
        technologyCard: ProgressCard.TechnologyCard
    ): ActionResult<Information> {
        val currentTechnologyCard =
            game.technologyBoard.getCurrentTechnologyCard(technologyCard.technology, targetPosition)

        val (canRecycle, moneyToRecycle) =
            if (currentTechnologyCard == null) {
                Pair(false, null)
            } else {
                val moneyToRecycle = currentTechnologyCard.values.moneyCosts
                val moneyAfterPurchase = game.money - progressCard.values.moneyCosts
                val hasEnoughMoney = moneyAfterPurchase >= moneyToRecycle
                Pair(hasEnoughMoney, moneyToRecycle)
            }

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

    private fun handleClimateCard(game: Game, climateCard: ProgressCard.ClimateCard): ActionResult<Information> {
        return ActionResult(
            game,
            Information(false, null, climateCard.values.moneyCosts, climateCard.values.resourceCosts)
        )
    }

    data class Information(
        val canRecycle: Boolean,
        val moneyToRecycle: Int?,
        val moneyToPay: Int,
        val resourcesToPay: Int,
    )
}
