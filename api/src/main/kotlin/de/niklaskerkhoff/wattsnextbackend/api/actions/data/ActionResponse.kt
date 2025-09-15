package de.niklaskerkhoff.wattsnextbackend.api.actions.data

import de.niklaskerkhoff.wattsnextbackend.api.actions.data.responsemodel.GameData
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result

data class ActionResponse<T>(
    val game: GameData,
    val status: Status,
    val baseInformation: Game.BaseInformation? = null,
    val actionInformation: T? = null,
    val cardEffectInformation: Any? = null,
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

    constructor(result: Result<T>, status: Status) : this(
        game = GameData(result),
        status = status,
        baseInformation = result.baseInformation,
        actionInformation = result.actionInformation,
        cardEffectInformation = result.cardEffectInformation,
    )
}
