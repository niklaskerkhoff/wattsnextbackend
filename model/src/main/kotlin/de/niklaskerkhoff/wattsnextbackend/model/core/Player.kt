package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.replacedFirst
import java.util.UUID

data class Player(
    val name: String,
    val progressCards: List<ProgressCard?>,
    private val id: UUID = UUID.randomUUID(),
) {
    val publicId get() = id

    fun replaceProgressCard(replacementCard: ProgressCard?, cardToReplace: ProgressCard?) = copy(
        progressCards = progressCards.replacedFirst(replacementCard, cardToReplace)
    )
}
