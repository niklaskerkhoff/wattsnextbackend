package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard


data class CommonAssets(
    val progressCardDeck: List<ProgressCard>,
    val standardEventCardDeck: List<EventCard>,
    val catastropheEventCardDeck: List<EventCard>,

    val money: Int,
    val resources: Int,

    val technologyBoard: TechnologyBoard,


    val standardEventCard: EventCard? = null,
    val catastropheEventCard: EventCard? = null,
) {

    fun getAllCards() =
        technologyBoard.getAllCurrentProgressCards() + standardEventCard + catastropheEventCard
}
