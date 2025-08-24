package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.ActionResult
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModificationApplier
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.WithTargetPositionModifierProvider
import kotlin.math.floor

class PlayTechnologyCardActionIntent(
    internal val technologyCard: ProgressCard.TechnologyCard,
    internal val targetPosition: Int,
) : Action<PlayTechnologyCardActionIntent.Information>() {
    override fun canExecute(game: Game): Boolean {
        return game.money >= technologyCard.values.moneyCosts && game.resources >= technologyCard.values.resourceCosts
    }

    override fun execute(game: Game): ActionResult<Information> {
        val currentTechnologyCard =
            game.technologyBoard.getCurrentTechnologyCard(technologyCard.technology, targetPosition)


        val gainingResources = currentTechnologyCard?.let { floor(it.values.resourceCosts / 2.0).toInt() }

        val (canRecycle, moneyToRecycle) =
            if (currentTechnologyCard == null) {
                Pair(false, null)
            } else {
                val moneyToRecycle = currentTechnologyCard.values.moneyCosts
                val moneyAfterPurchase = game.money - technologyCard.values.moneyCosts
                val hasEnoughMoney = moneyAfterPurchase >= moneyToRecycle
                Pair(hasEnoughMoney, moneyToRecycle)
            }

        val moneyToPay = ModificationApplier(
            WithTargetPositionModifierProvider({ costModifier }, technologyCard, game, targetPosition),
            technologyCard.values.moneyCosts,
            game,
        ).applyModification()

        return ActionResult(
            game,
            Information(
                canRecycle = canRecycle,
                moneyForRecycling = moneyToRecycle,
                gainingResourcesForRecycling = gainingResources,
                moneyForPlayingCard = moneyToPay,
                resourcesForPlayingCard = technologyCard.values.resourceCosts,
            )
        )
    }

    data class Information(
        val canRecycle: Boolean,
        val moneyForRecycling: Int?,
        val gainingResourcesForRecycling: Int?,
        val moneyForPlayingCard: Int,
        val resourcesForPlayingCard: Int,
    )
}
