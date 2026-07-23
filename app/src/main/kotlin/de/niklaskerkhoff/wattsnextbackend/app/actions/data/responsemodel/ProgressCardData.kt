package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import java.util.*

private fun List<Card>.toCardModifications(): List<Modification> =
    map { Modification.Card(it.name) }

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
    val type: String, // 'technology' or 'climateAction'
    val phase: Int // 1-indexed phase the card belongs to (I, II, III)
) {
    constructor(card: ProgressCard, result: Result<*>, targetPosition: Int? = null) : this(
        id = card.publicId,
        name = card.name,
        image = card.imageSrc,
        text = card.requirementsDescription,
        explanation = card.explanation,
        moneyCosts = ModifiableValue(
            originalValue = card.moneyCosts.base,
            modifiedValue = result.game.getModifiedMoneyCost(card, targetPosition ?: -1),
            modifications = result.game.getMoneyCostModifications(card, targetPosition ?: -1).toCardModifications()
        ),
        resourceCosts = ModifiableValue(
            originalValue = card.resourceCosts.base,
            modifiedValue = result.game.getModifiedResourceCost(card, targetPosition ?: -1),
            modifications = result.game.getResourceCostModifications(card, targetPosition ?: -1).toCardModifications()
        ),
        points = ModifiableValue(
            originalValue = ProgressPointsData.from(card, result, false),
            modifiedValue = ProgressPointsData.from(card, result, true),
            modifications = result.game.getPointConditionModifications(card).toCardModifications()
        ),
        supply = ModifiableValue(
            originalValue = card.supply.base,
            modifiedValue = card.supply.modified(card, result.game, Pair(result.game, targetPosition ?: -1)),
            modifications = result.game.getSupplyModifications(card, targetPosition ?: -1).toCardModifications()
        ),
        isPlayable = result.game.getPlayableHandcards(targetPosition).contains(card),
        gameBeforeEffect = null, // TODO (or delete)
        type = when (card) {
            is ProgressCard.TechnologyCard -> "technology"
            is ProgressCard.ClimateCard -> "climateAction"
        },
        phase = card.phaseIndex + 1,
    )
}
