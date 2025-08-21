package de.niklaskerkhoff.wattsnextbackend.api.actions

import java.util.UUID

data class PlayCardActionIntentRequest(
    val progressCardId: UUID,
    val targetPosition: Int,
)

data class PlayCardActionRequest(
    val shallRecycle: Boolean,
)

// TODO: possible Actions not up-to-date, add action-specific responses
data class ActionResponse<T>(
    val game: GameDto,
    val information: T,
    val possibleActions: List<Any>
)
