package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInformation

data class Result<T>(
    val game: Game,
    val baseInformation: Game.BaseInformation? = null,
    val actionInformation: T? = null,
    val cardEffectInformations: List<CardEffectInformation> = emptyList(),
)
