package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import java.util.UUID

class EventCard(
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val explanation: String,
    override val imageSrc: String,
    override val modifierCollection: ModifierCollection,
    override val effect: CardEffect,
    override val phase: Int,
    val isCatastrophe: Boolean,
) : Card()
