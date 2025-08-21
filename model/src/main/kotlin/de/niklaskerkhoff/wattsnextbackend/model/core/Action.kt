package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.actions.ActionResult

abstract class Action<T> {
    abstract fun canExecute(game: Game): Boolean
    abstract fun execute(game: Game): ActionResult<T>
}
