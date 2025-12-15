package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.SimpleModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.WithIntModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import java.util.*

data class TechnologyCardData(
    val id: String,
    val name: String,
    val modifierCollection: ModifierCollection? = null,
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

    val tags: List<Tag> = emptyList(),
) {
    fun toTechnologyCard() =
        ProgressCard.TechnologyCard(
            id = UUID.fromString(id),
            name = name,
            modifierCollection = if (modifierCollection == null && supply.size == 1) ModifierCollection(
                supplyModifierConfig = ModifierConfig(
                    rank = 10,
                    modify = SupplyModifier.Stack.modify,
                )
            ) else ModifierCollection(),
            effect = effect,
            phaseIndex = phaseIndex,

            supply = WithIntModifiedValue(supply) { supplyModifierConfig } as WithIntModifiedValue<Supply.Energy>,
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
    val id: String,
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
            id = UUID.fromString(id),
            name = name,
            modifierCollection = modifierCollection,
            effect = effect,
            phaseIndex = phaseIndex,

            supply = WithIntModifiedValue(supply) { supplyModifierConfig } as WithIntModifiedValue<Supply.Achievement?>,
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
