package de.niklaskerkhoff.wattsnextbackend.model.state

abstract class Action<T> {
    internal abstract fun canExecute(): Boolean
    internal abstract fun execute(): T
}