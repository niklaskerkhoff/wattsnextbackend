package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import java.util.*

data class ProgressCardData(
    val id: UUID,
    val name: String,
    val image: String,
    val text: String,
    val explanation: String,
    val moneyCosts: ModifiableValue<Int>,
    val resourceCosts: ModifiableValue<Int>,
    val points: ModifiableValue<ProgressPointsData>?,
    val supply: ModifiableValue<Supply?>,
    val isPlayable: Boolean,
    val gameBeforeEffect: GameData?,
    val type: String // 'technology' or 'climateAction'
) {
    constructor(card: ProgressCard, result: Result<*>, targetPosition: Int? = null) : this(
        id = card.publicId,
        name = card.name,
        image = card.imageSrc,
        text = card.requirementsDescription,
        explanation = card.explanation,
        moneyCosts = ModifiableValue(
            originalValue = card.moneyCosts.base,
            modifiedValue = card.moneyCosts.modified(card, result.game, Pair(result.game, targetPosition ?: -1))
        ),
        resourceCosts = ModifiableValue(
            originalValue = card.resourceCosts.base,
            modifiedValue = card.resourceCosts.modified(card, result.game, Pair(result.game, targetPosition ?: -1))
        ),
        points = ModifiableValue(
            originalValue = ProgressPointsData.from(card, result, false),
            modifiedValue = ProgressPointsData.from(card, result, true)
        ),
        supply = ModifiableValue(
            originalValue = card.supply.base,
            modifiedValue = card.supply.modified(card, result.game, Pair(result.game, targetPosition ?: -1))
        ),
        isPlayable = result.game.getPlayableHandcards(targetPosition).contains(card),
        gameBeforeEffect = null, // TODO (or delete)
        type = when (card) {
            is ProgressCard.TechnologyCard -> "technology"
            is ProgressCard.ClimateCard -> "climateAction"
        },
    )
}
