package de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Player
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import java.util.*

data class PlayerData(
    val id: UUID,
    val name: String,
    val handCards: List<ProgressCardData?>
) {
    constructor(player: Player, result: Result<*>) : this(
        id = player.publicId,
        name = player.name,
        handCards = player.progressCards.map { it?.let { ProgressCardData(it, result) } }
    )
}
