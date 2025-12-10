package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology

data class PhaseData(
    val generation: TargetableValue,
    val distribution: TargetableValue,
    val storage: TargetableValue,
    val progressPoints: TargetableValue,
    val electricity: TargetableValue,
    val heat: TargetableValue,
) {
    constructor(result: Result<*>, phaseIndex: Int) : this(
        // TODO: Values have to be set correctly
        generation = TargetableValue(
            value = 0,
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No generation target found.")
        ),
        distribution = TargetableValue(
            value = 0,
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No distribution target found.")
        ),
        storage = TargetableValue(
            value = 0,
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No storage target found.")
        ),
        progressPoints = TargetableValue(
            value = 0,
            target = result.game.pointTargetsPerPhase[phaseIndex]
        ),
        electricity = TargetableValue(0, 0),
        heat = TargetableValue(0, 0),
    )
}
