package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard

class PlayTechnologyCardActionIntent(
    internal val technologyCard: ProgressCard.TechnologyCard,
    internal val targetPosition: Int,
) : Action<PlayTechnologyCardActionIntent.Info>() {
    override fun canExecute(game: Game): Boolean {
        return game.money >= technologyCard.moneyCosts.modified(technologyCard, game, Pair(game, targetPosition)) &&
                game.resources >= technologyCard.resourceCosts.modified(technologyCard, game, Pair(game, targetPosition))
    }

    override fun execute(game: Game): Result<Info> {
        // Recycling is an advanced-mode mechanic and is currently disabled. The mechanic itself
        // is kept in PlayTechnologyCardAction.recycle(); here it is simply reported as unavailable
        // so it is never offered to or executed for the client.
        return Result(
            game = game,
            actionInfo = Info(
                canRecycle = false,
                moneyForRecycling = null,
                gainingResourcesForRecycling = null,
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
