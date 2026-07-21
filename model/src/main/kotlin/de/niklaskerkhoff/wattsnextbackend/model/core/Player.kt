package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import java.util.UUID

data class Player(
    val name: String,
    val progressCards: List<ProgressCard?>,
    private val id: UUID,
) {
    val publicId get() = id
}
