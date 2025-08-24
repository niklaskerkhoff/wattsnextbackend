package de.niklaskerkhoff.wattsnextbackend.api.actions

import java.util.*


data class PlayCardActionIntentRequest(
    val progressCardId: UUID,
    val targetPosition: Int,
)

data class PlayCardActionRequest(
    val shallRecycle: Boolean,
)

data class ActionResponse<T>(
    val game: GameDto,
    val status: ResponseStatus,
    val information: T?,
)

enum class ResponseStatus {
    OK,
    ILLEGAL_ACTION_ARGUMENTS,
    ILLEGAL_ACTION,
}
