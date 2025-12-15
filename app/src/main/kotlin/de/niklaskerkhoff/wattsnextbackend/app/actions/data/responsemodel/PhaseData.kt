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
        generation = TargetableValue(
            value = result.game.getGeneration(),
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No generation target found.")
        ),
        distribution = TargetableValue(
            value = result.game.getDistribution(),
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No distribution target found.")
        ),
        storage = TargetableValue(
            value = result.game.getStorage(),
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No storage target found.")
        ),
        progressPoints = TargetableValue(
            value = result.game.calculateProgressPointInfo().progressPoints,
            target = result.game.pointTargetsPerPhase[phaseIndex]
        ),
        electricity = TargetableValue(
            value = if (result.game.doesElectricityExist()) 1 else 0,
            target = 1
        ),
        heat = TargetableValue(
            value = if (result.game.doesHeatExist()) 1 else 0,
            target = 1
        ),
    )
}
