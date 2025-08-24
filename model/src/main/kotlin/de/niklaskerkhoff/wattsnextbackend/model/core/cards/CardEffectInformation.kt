package de.niklaskerkhoff.wattsnextbackend.model.core.cards

sealed class CardEffectInformation {
    data class Money(val amount: Int) : CardEffectInformation()
    data class Resource(val amount: Int) : CardEffectInformation()
}
