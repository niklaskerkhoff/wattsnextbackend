package de.niklaskerkhoff.wattsnextbackend.api.actions.data

import de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel.ProgressCardData
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInformation
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

data class ActionResponse(
    val game: GameData,
    val status: Status,
    val baseInformation: Game.BaseInformation? = null,
    val actionInformation: Map<String, Any>? = null,
    val cardEffectInformation: List<CardEffectInformation> = emptyList(),
) {
    enum class Status {
        Ok,
        IllegalActionArguments,
        IllegalAction,
    }

    constructor(game: Game, status: Status) : this(
        game = GameData(Result<Unit>(game)),
        status = status,
    )

    constructor(result: Result<*>, status: Status) : this(
        game = GameData(result),
        status = status,
        baseInformation = result.baseInformation,
        actionInformation = getActionInformationDto(result),
        cardEffectInformation = result.cardEffectInformations,
    )

    companion object {
        private fun getActionInformationDto(result: Result<*>): Map<String, Any>? {
            val actionInformation = result.actionInformation ?: return null

            val resultMap = mutableMapOf<String, Any>()

            for (prop in actionInformation::class.memberProperties) {
                prop.isAccessible = true
                val raw = prop.getter.call(actionInformation) ?: continue

                val value: Any =
                    when (raw) {
                        is Int -> raw
                        is Boolean -> raw
                        is ProgressCard -> ProgressCardData(raw, result)
                        else -> throw IllegalArgumentException("Unknown type: ${raw::class.simpleName}")
                    }

                resultMap[prop.name] = value
            }

            return resultMap
        }
    }
}
