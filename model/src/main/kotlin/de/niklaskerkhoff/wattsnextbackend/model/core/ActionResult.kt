package de.niklaskerkhoff.wattsnextbackend.model.core

data class ActionResult<T>(
    val game: Game,
    val information: T
)
