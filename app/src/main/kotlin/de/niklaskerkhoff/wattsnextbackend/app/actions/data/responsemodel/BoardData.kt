package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard

data class BoardData(
    val generationCards: List<ProgressCardData?>,
    val distributionCards: List<ProgressCardData?>,
    val storageCards: List<ProgressCardData?>,
    val climateActionCards: List<ProgressCardData>,
    val eventCards: List<EventCardData>,
    val catastropheCard: EventCardData? = null,
) {
    constructor(
        generationCards: List<List<ProgressCard.TechnologyCard>>,
        distributionCards: List<List<ProgressCard.TechnologyCard>>,
        storageCards: List<List<ProgressCard.TechnologyCard>>,
        climateActionCards: List<ProgressCard.ClimateCard>,
        eventCards: List<EventCard>,
        catastropheCard: EventCard?,
        result: Result<*>,
    ) : this(
        generationCards =
            generationCards.mapIndexed { index, column ->
                column.lastOrNull()?.let { card -> ProgressCardData(card, result, index) }
            },
        distributionCards =
            distributionCards.mapIndexed { index, column ->
                column.lastOrNull()?.let { card -> ProgressCardData(card, result, index) }
            },
        storageCards =
            storageCards.mapIndexed { index, column ->
                column.lastOrNull()?.let { card -> ProgressCardData(card, result, index) }
            },
        climateActionCards = climateActionCards.map { card -> ProgressCardData(card, result) },
        eventCards = eventCards.map { card -> EventCardData(card, result) },
        catastropheCard = catastropheCard?.let { card -> EventCardData(card, result) },
    )
}
