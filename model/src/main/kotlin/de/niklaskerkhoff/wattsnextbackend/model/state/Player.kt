package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst

data class Player(
    val name: String,
    val progressCards: List<ProgressCard?>
) {
    fun replaceProgressCard(replacementCard: ProgressCard?, cardToReplace: ProgressCard?) = copy(
        progressCards = progressCards.replacedFirst(replacementCard, cardToReplace)
    )
}
