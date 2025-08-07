package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.actions.ActionResult

abstract class Action<T> {
    internal abstract fun canExecute(game: Game): Boolean
    internal abstract fun execute(game: Game): ActionResult<T>
}