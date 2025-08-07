package de.niklaskerkhoff.wattsnextbackend.model.lib

fun <T> List<T>.replacedFirst(previousItem: T, updatedItem: T): List<T> {
    val index = indexOfFirst { it == previousItem }
    if (index < 0) return this
    return toMutableList().apply { this[index] = updatedItem }
}

fun <T> List<T>.removedLast(): List<T> = toMutableList().apply { removeLast() }

fun <T> List<T>.removed(item: T): List<T> = toMutableList().apply { remove(item) }
