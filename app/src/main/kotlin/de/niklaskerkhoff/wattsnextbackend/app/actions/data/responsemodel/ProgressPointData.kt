package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply

data class ProgressPointsData(
    val baseProgressPoints: Int?,
    val systemProgressPoints: Int,
    val conditions: List<Supply>,
    val conditionsFulfilled: Boolean,
) {
    constructor(card: ProgressCard, result: Result<*>) : this(
        baseProgressPoints = card.basePoints,
        systemProgressPoints = card.systemPoints,
        // TODO: I have no idea what I am doing here
        conditions = card.supplyRequirementsForSystem.modified(card, result.game, result.game),
        conditionsFulfilled = false,
    )
}
