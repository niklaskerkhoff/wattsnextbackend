package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply
import java.util.UUID

sealed class ProgressCard : Card() {

    abstract val values: ProgressCardValues
    abstract val explanation: String
    abstract val requirementsDescription: String
    abstract val imageSrc: String

    class TechnologyCard(
        override val id: UUID,
        override val name: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val phase: Int,
        override val values: ProgressCardValues,
        override val explanation: String,
        override val requirementsDescription: String,
        override val imageSrc: String,
        val energy: Supply.Energy
    ) : ProgressCard()

    class ClimateCard(
        override val id: UUID,
        override val name: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val phase: Int,
        override val values: ProgressCardValues,
        override val explanation: String,
        override val requirementsDescription: String,
        override val imageSrc: String,
        val achievement: Supply.Achievement?,
    ) : ProgressCard()
}
