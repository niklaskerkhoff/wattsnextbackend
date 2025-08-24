package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply
import java.util.UUID

sealed class ProgressCard : Card() {

    abstract val values: ProgressCardValues

    class TechnologyCard(
        override val id: UUID,
        override val name: String,
        override val description: String,
        override val explanation: String,
        override val imageSrc: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val values: ProgressCardValues,
        override val phase: Int,
        val energy: Supply.Energy
    ) : ProgressCard()

    class ClimateCard(
        override val id: UUID,
        override val name: String,
        override val description: String,
        override val explanation: String,
        override val imageSrc: String,
        override val modifierCollection: ModifierCollection,
        override val effect: CardEffect,
        override val values: ProgressCardValues,
        override val phase: Int,
        val achievement: Supply.Achievement?,
    ) : ProgressCard()
}
