package de.niklaskerkhoff.wattsnextbackend.model.core

data class Result<T>(
    val game: Game,
    val baseInformation: Game.BaseInformation? = null,
    val actionInformation: T? = null,
    val cardEffectInformation: Any? = null
)
