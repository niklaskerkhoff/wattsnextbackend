package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.state.Game

data class ActionResult<T>(
    val game: Game,
    val information: T
)