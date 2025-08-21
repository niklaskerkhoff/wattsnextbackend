package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard


data class CommonAssets(




    val technologyBoard: TechnologyBoard,


    val standardEventCard: EventCard? = null,
    val catastropheEventCard: EventCard? = null,
) {

    fun getAllCards() =
        technologyBoard.getAllCurrentProgressCards() + standardEventCard + catastropheEventCard
}
