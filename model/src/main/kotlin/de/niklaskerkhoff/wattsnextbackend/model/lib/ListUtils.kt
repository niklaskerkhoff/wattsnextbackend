package de.niklaskerkhoff.wattsnextbackend.model.lib

fun <T> List<T>.replacedFirst(previousItem: T, updatedItem: T): List<T> {
    val index = indexOfFirst { it == previousItem }
    if (index < 0) return this
    return toMutableList().apply { this[index] = updatedItem }
}

fun <T> List<T>.removedLast(): Pair<T, List<T>> = Pair(last(), toMutableList().apply { removeLast() })

fun <T> List<T>.removed(item: T): List<T> = toMutableList().apply { remove(item) }

inline fun <reified A, reified B> List<*>.partitionByType(): Pair<List<A>, List<B>> {
    val first = mutableListOf<A>()
    val second = mutableListOf<B>()

    forEach {
        if (it is A) first.add(it)
        else if (it is B) second.add(it)
    }

    return Pair(first, second)
}
