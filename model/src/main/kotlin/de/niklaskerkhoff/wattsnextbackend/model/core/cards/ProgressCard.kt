package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology
import java.util.*

sealed class ProgressCard : Card() {

    abstract val supply: WithIntModifiedValue<Supply?>
    abstract val basePoints: Int
    abstract val systemPoints: Int
    abstract val supplyRequirementsForSystem: SimpleModifiedValue<List<Supply>>
    abstract val moneyCosts: WithIntModifiedValue<Int>
    abstract val resourceCosts: WithIntModifiedValue<Int>

    abstract val explanation: String
    abstract val requirementsDescription: String
    abstract val imageSrc: String

    class TechnologyCard(
        override val id: UUID,
        override val name: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val phaseIndex: Int,

        override val supply: WithIntModifiedValue<Supply.Energy>,
        override val basePoints: Int,
        override val systemPoints: Int,
        override val supplyRequirementsForSystem: SimpleModifiedValue<List<Supply>>,
        override val moneyCosts: WithIntModifiedValue<Int>,
        override val resourceCosts: WithIntModifiedValue<Int>,

        override val explanation: String,
        override val requirementsDescription: String,
        override val imageSrc: String,

        val tags: List<Tag> = emptyList(),
    ) : ProgressCard() {
        val technology: Technology get() = supply.base.technology
    }

    class ClimateCard(
        override val id: UUID,
        override val name: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val phaseIndex: Int,

        override val supply: WithIntModifiedValue<Supply.Achievement?>,
        override val basePoints: Int,
        override val systemPoints: Int,
        override val supplyRequirementsForSystem: SimpleModifiedValue<List<Supply>>,
        override val moneyCosts: WithIntModifiedValue<Int>,
        override val resourceCosts: WithIntModifiedValue<Int>,

        override val explanation: String,
        override val requirementsDescription: String,
        override val imageSrc: String,
    ) : ProgressCard()
}
