package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.model.config.climateCards
import de.niklaskerkhoff.wattsnextbackend.model.config.eventCards
import de.niklaskerkhoff.wattsnextbackend.model.config.technologyCards
import de.niklaskerkhoff.wattsnextbackend.model.core.Player
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import java.util.*

class EntityResolver(
    players: List<Player>,
) {
    private val playerMap: Map<UUID, Player> = players.associateBy { it.publicId }
    private val technologyCardMap: Map<UUID, ProgressCard.TechnologyCard> = technologyCards.associateBy { it.publicId }
    private val climateCardMap: Map<UUID, ProgressCard.ClimateCard> = climateCards.associateBy { it.publicId }
    private val eventCardMap: Map<UUID, EventCard> = eventCards.associateBy { it.publicId }

    fun getPlayer(id: UUID): Player? = playerMap[id]
    fun getTechnologyCard(id: UUID): ProgressCard.TechnologyCard? = technologyCardMap[id]
    fun getClimateCard(id: UUID): ProgressCard.ClimateCard? = climateCardMap[id]
    fun getEventCard(id: UUID): EventCard? = eventCardMap[id]
}
