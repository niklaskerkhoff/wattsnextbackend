package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard.EffectDescription
import java.util.*

data class EventCardData(
    val id: UUID,
    val name: String,
    val phaseIndex: Int,
    val isCatastrophe: Boolean,
    val eventDescription: String,
    val effectDescriptions: List<EffectDescription>,
    val effectConditionDescription: String? = null,
) {
    constructor(eventCard: EventCard, result: Result<*>) : this(
        id = eventCard.publicId,
        name = eventCard.name,
        phaseIndex = eventCard.phaseIndex,
        isCatastrophe = eventCard.isCatastrophe,
        eventDescription = eventCard.eventDescription,
        effectDescriptions = eventCard.effectDescriptions,
        effectConditionDescription = eventCard.effectConditionDescription,
    )
}
