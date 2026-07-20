package de.niklaskerkhoff.wattsnextbackend.app.actions.data

import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel.ProgressCardData
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInfo
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

data class ActionResponse(
    val game: GameData,
    val status: Status,
    val baseInfo: Game.BaseInfo? = null,
    val actionInfo: Map<String, Any>? = null,
    val cardEffectInfo: List<CardEffectInfo> = emptyList(),
    val eventEffectInfo: List<CardEffectInfo> = emptyList(),
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
        baseInfo = result.baseInfo,
        actionInfo = getActionInfoDto(result),
        cardEffectInfo = result.cardEffectInfos,
        eventEffectInfo = result.eventEffectInfos,
    )

    companion object {
        private fun getActionInfoDto(result: Result<*>): Map<String, Any>? {
            val actionInfo = result.actionInfo ?: return null

            val targetPosition =
                actionInfo::class.memberProperties
                    .find { it.name == "targetPosition" }
                    ?.getter?.call(actionInfo) as? Int?

            val resultMap = mutableMapOf<String, Any>()

            for (prop in actionInfo::class.memberProperties) {
                prop.isAccessible = true
                val raw = prop.getter.call(actionInfo) ?: continue

                val value: Any =
                    when (raw) {
                        is Int -> raw
                        is Boolean -> raw
                        is String -> raw
                        is ProgressCard -> ProgressCardData(raw, result, targetPosition)
                        else -> throw IllegalArgumentException("Unknown type: ${raw::class.simpleName}")
                    }

                resultMap[prop.name] = value
            }

            return resultMap
        }
    }
}
