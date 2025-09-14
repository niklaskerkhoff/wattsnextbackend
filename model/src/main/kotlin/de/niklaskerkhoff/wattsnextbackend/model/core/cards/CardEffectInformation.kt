package de.niklaskerkhoff.wattsnextbackend.model.core.cards

sealed class CardEffectInformation {
    data class Money(val amount: Int) : CardEffectInformation()
    data class Resources(val amount: Int) : CardEffectInformation()
    data class ProgressPoints(val amount: Int) : CardEffectInformation()
}
