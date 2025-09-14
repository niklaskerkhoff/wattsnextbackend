package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import java.util.*

class EventCard(
    override val id: UUID,
    override val name: String,
    override val modifierCollection: ModifierCollection,
    override val effect: CardEffect,
    override val phaseIndex: Int,
    val isCatastrophe: Boolean,
    val eventDescription: String,
    val effectDescriptions: List<EffectDescription>,
    val effectConditionDescription: String? = null,
    val footnote: String? = null,
) : Card() {
    data class EffectDescription(
        val text: String,
        val imageSrc: String?
    )
}
