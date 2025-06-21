package de.niklaskerkhoff.wattsnextbackend.model.modifiers

typealias UnifiedModifier<T> = Modifier<(acc: T) -> T>

class Modifier<Function>(
    val rank: Int,
    val modify: Function,
)
