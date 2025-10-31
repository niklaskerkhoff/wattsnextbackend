package de.niklaskerkhoff.wattsnextbackend.model.core.cards

sealed class CardEffectInfo {
    data class Money(val amount: Int) : CardEffectInfo()
    data class Resources(val amount: Int) : CardEffectInfo()
    data class ProgressPoints(val amount: Int) : CardEffectInfo()
}
