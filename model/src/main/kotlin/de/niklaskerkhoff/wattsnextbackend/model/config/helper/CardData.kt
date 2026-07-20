package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.SimpleModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.WithIntModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
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
            // A provided collection is kept as-is (previously it was silently dropped).
            // No auto-stack modifier is injected: stacking (summing same-name cards on a
            // slot) is an advanced-mode mechanic. In the standard mode a card played onto an
            // occupied slot simply overbuilds it. The mechanic itself is still available via
            // SupplyModifier.Stack / TechnologyBoard.getSameTechnologyCardStack and must be
            // wired up again (ideally per-game) once an advanced mode is introduced.
            modifierCollection = modifierCollection ?: ModifierCollection(),
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
