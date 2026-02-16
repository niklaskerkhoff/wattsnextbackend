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
    companion object {
        fun from(
            card: ProgressCard,
            result: Result<*>,
            modified: Boolean
        ): ProgressPointsData {

            val points = result.game.calculateProgressPoints(card, modified)

            return ProgressPointsData(
                baseProgressPoints = points.basePoints,
                systemProgressPoints = points.systemPoints,
                conditions = points.conditions,
                conditionsFulfilled = points.conditionsFulfilled
            )
        }
    }
}
