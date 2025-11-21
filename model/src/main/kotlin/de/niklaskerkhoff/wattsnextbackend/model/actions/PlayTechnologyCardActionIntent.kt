package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import kotlin.math.floor

class PlayTechnologyCardActionIntent(
    internal val technologyCard: ProgressCard.TechnologyCard,
    internal val targetPosition: Int,
) : Action<PlayTechnologyCardActionIntent.Info>() {
    override fun canExecute(game: Game): Boolean {
        return game.money >= technologyCard.moneyCosts.modified(technologyCard, game, Pair(game, targetPosition)) &&
                game.resources >= technologyCard.resourceCosts.modified(technologyCard, game, Pair(game, targetPosition))
    }

    override fun execute(game: Game): Result<Info> {
        val currentTechnologyCard =
            game.technologyBoard.getCurrentTechnologyCard(technologyCard.supply.base.technology, targetPosition)


        val gainingResources = currentTechnologyCard?.let { floor(it.resourceCosts.base / 2.0).toInt() }

        val (canRecycle, moneyToRecycle) =
            if (currentTechnologyCard == null) {
                Pair(false, null)
            } else {
                val moneyToRecycle = currentTechnologyCard.resourceCosts.base
                val moneyAfterPurchase = game.money - moneyToRecycle
                val hasEnoughMoney = moneyAfterPurchase >= moneyToRecycle
                Pair(hasEnoughMoney, moneyToRecycle)
            }

        return Result(
            game = game,
            actionInfo = Info(
                canRecycle = canRecycle,
                moneyForRecycling = moneyToRecycle,
                gainingResourcesForRecycling = gainingResources,
                moneyForPlayingCard = technologyCard.moneyCosts.modified(technologyCard, game, Pair(game, targetPosition)),
                resourcesForPlayingCard = technologyCard.resourceCosts.modified(technologyCard, game, Pair(game, targetPosition)),
            )
        )
    }

    data class Info(
        val canRecycle: Boolean,
        val moneyForRecycling: Int?,
        val gainingResourcesForRecycling: Int?,
        val moneyForPlayingCard: Int,
        val resourcesForPlayingCard: Int,
    )
}
