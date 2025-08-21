package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.Game

class EventCard(
    override val name: String,
    override val description: String,
    override val imageSrc: String,
    override val modifierCollection: ModifierCollection,
    override val effect: ((Game) -> Game)?,
    val isCatastrophe: Boolean,
) : Card()
