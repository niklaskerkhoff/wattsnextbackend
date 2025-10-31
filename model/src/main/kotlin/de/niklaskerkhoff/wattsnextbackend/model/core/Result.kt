package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInfo

data class Result<T>(
    val game: Game,
    val baseInfo: Game.BaseInfo? = null,
    val actionInfo: T? = null,
    val cardEffectInfos: List<CardEffectInfo> = emptyList(),
)
