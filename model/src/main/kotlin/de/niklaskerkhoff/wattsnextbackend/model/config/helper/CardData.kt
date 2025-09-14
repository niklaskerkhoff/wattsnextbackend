package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.SimpleModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.WithIntModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import java.util.*

data class TechnologyCardData(
    val id: UUID,
    val name: String,
    val modifierCollection: ModifierCollection,
    val effect: CardEffect = { Pair(it, emptyList()) },
    val phaseIndex: Int,

    val supply: Supply.Energy,
    val basePoints: Int,
    val systemPoints: Int,
    val supplyRequirementsForSystem: List<Supply>,
    val moneyCosts: Int,
    val resourceCosts: Int,

    val explanation: String,
    val requirementsDescription: String,
    val imageSrc: String,

    val tags: List<String> = emptyList(),
) {
    fun toTechnologyCard() =
        ProgressCard.TechnologyCard(
            id = id,
            name = name,
            modifierCollection = modifierCollection,
            effect = effect,
            phaseIndex = phaseIndex,

            supply = supply,
            basePoints = basePoints,
            systemPoints = systemPoints,
            supplyRequirementsForSystem =
                SimpleModifiedValue(supplyRequirementsForSystem) { supplyRequirementsForSystemModifierConfig },
            moneyCosts = WithIntModifiedValue(moneyCosts) { cardMoneyCostsModifierConfig },
            resourceCosts = WithIntModifiedValue(resourceCosts) { cardResourceCostsModifierConfig },

            explanation = explanation,
            requirementsDescription = requirementsDescription,
            imageSrc = imageSrc,

            tags = tags,
        )
}

data class ClimateCardData(
    val id: UUID,
    val name: String,
    val modifierCollection: ModifierCollection,
    val effect: CardEffect = { Pair(it, emptyList()) },
    val phaseIndex: Int,

    val supply: Supply.Achievement?,
    val basePoints: Int,
    val systemPoints: Int,
    val supplyRequirementsForSystem: List<Supply>,
    val moneyCosts: Int,
    val resourceCosts: Int,

    val explanation: String,
    val requirementsDescription: String,
    val imageSrc: String,
) {
    fun toClimateCard() =
        ProgressCard.ClimateCard(
            id = id,
            name = name,
            modifierCollection = modifierCollection,
            effect = effect,
            phaseIndex = phaseIndex,

            supply = supply,
            basePoints = basePoints,
            systemPoints = systemPoints,
            supplyRequirementsForSystem =
                SimpleModifiedValue(supplyRequirementsForSystem) { supplyRequirementsForSystemModifierConfig },
            moneyCosts = WithIntModifiedValue(moneyCosts) { cardMoneyCostsModifierConfig },
            resourceCosts = WithIntModifiedValue(resourceCosts) { cardResourceCostsModifierConfig },

            explanation = explanation,
            requirementsDescription = requirementsDescription,
            imageSrc = imageSrc,
        )
}
