package de.niklaskerkhoff.wattsnextbackend.api.actions.data

import java.util.*


data class PlayCardActionIntentRequest(
    val progressCardId: UUID,
    val targetPosition: Int,
)

data class PlayCardActionRequest(
    val shallRecycle: Boolean,
)
