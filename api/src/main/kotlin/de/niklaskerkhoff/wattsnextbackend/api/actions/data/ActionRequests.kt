package de.niklaskerkhoff.wattsnextbackend.api.actions.data

import java.util.*

data class PlayClimateCardRequest(
    val climateCardId: UUID,
)

data class PlayTechnologyCardIntentRequest(
    val progressCardId: UUID,
    val targetPosition: Int,
)

data class PlayTechnologyCardRequest(
    val shallRecycle: Boolean,
)
