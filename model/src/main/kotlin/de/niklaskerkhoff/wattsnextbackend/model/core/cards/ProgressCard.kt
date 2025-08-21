package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.Game

sealed class ProgressCard : Card() {

    abstract val values: ProgressCardValues

    class TechnologyCard(
        override val name: String,
        override val description: String,
        override val imageSrc: String,
        override val modifierCollection: ModifierCollection,
        override val effect: ((Game) -> Game)?,
        override val values: ProgressCardValues,
        val technology: Technology,
    ) : ProgressCard()

    class ClimateCard(
        override val name: String,
        override val description: String,
        override val imageSrc: String,
        override val modifierCollection: ModifierCollection,
        override val effect: ((Game) -> Game)?,
        override val values: ProgressCardValues,
    ) : ProgressCard()
}
