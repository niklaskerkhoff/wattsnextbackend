package de.niklaskerkhoff.wattsnextbackend.model.core

abstract class Action<T> {
    abstract fun canExecute(game: Game): Boolean
    abstract fun execute(game: Game): Result<T>
}
