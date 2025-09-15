package de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel

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
    val supply: Supply?,
    val isPlayable: Boolean,
    val gameBeforeEffect: GameData?,
    // TODO: Is this the right way of differentiating between technology and climate action?
    val type: String // 'technology' or 'climateAction'
) {
    constructor(card: ProgressCard, result: Result<*>) : this(
        id = card.publicId,
        name = card.name,
        image = card.imageSrc,
        text = card.requirementsDescription,
        explanation = card.explanation,
        moneyCosts = ModifiableValue(
            originalValue = card.moneyCosts.base,
            modifiedValue = card.moneyCosts.modified(card, result.game, Pair(result.game, -1))
        ),
        resourceCosts = ModifiableValue(
            originalValue = card.resourceCosts.base,
            modifiedValue = card.resourceCosts.modified(card, result.game, Pair(result.game, -1))
        ),
        points = ModifiableValue(
            originalValue = ProgressPointsData(card, result),
            modifiedValue = ProgressPointsData(card, result)
        ),
        supply = card.supply,
        isPlayable = /*result.playableCards.any { it.id == card.publicId },*/ true,
        gameBeforeEffect = null,
        type = when (card) {
            is ProgressCard.TechnologyCard -> "technology"
            is ProgressCard.ClimateCard -> "climateAction"
        },
    )
}
