package de.niklaskerkhoff.wattsnextbackend.app.actions.data

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

data class ChangeCardRequest(
    val progressCardId: UUID ,
)

data class AnswerQuizRequest(
    val optionIndex: Int,
)
